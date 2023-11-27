// export const normalizeEventName = (name: string): string => name.replaceAll("-", "").toLowerCase()

// export const handles = (handledEvents: string[], input: {[key: string]: any}) => {
//     if(!handledEvents) return false

//     return handledEvents.map(normalizeEventName).includes(normalizeEventName)
// }

// static Boolean handles(List<String> events, Map input) {
//     if (events == null) {
//         return false
//     }

//     println "handles " + events.inspect()

//     return events
//             .collect { normalizeEventName(it) }
//             .contains(normalizeEventName(input["type"] as String))
// }

// // FIXME: Rewrite as standard Jenkins log
// //  to have a stack pretty printed, we use our own stack trace writer
// static String printStackTrace(Exception exception) {
//     StringWriter writer = new StringWriter()
//     PrintWriter printWriter = new PrintWriter(writer)
//     exception.printStackTrace(printWriter)
//     printWriter.flush()

//     return writer.toString()
// }

// static Boolean glob(String pattern, String path) {
//     return FileSystems.getDefault().getPathMatcher("glob:${pattern}").matches(Paths.get(path))
// }

// static Boolean isHandling(String workflowFileName = null, Map workflow, Map input) {

//     if (input != null && input['additionalProperties'] != null) {
//         if (input['additionalProperties']['workflow'] != null) {
//             def res2 = workflowFileName =~ /^\.pipeline\/workflows\/(.+)\.y?ml$/
//             if (res2.size() > 0) {
//                 def res = res2[0]
//                 if (res.size() == 2 && !(res[1] == input['additionalProperties']['workflow'])) {
//                     return false;
//                 }
//             }
//         }
//     }

//     if (workflow == null) {
//         throw new Exception("Workflow should not be null")
//     }
//     if (input == null) {
//         throw new Exception("Input should not be null")
//     }

//     def on = workflow['on']

//     if (on == null) {
//         throw new Exception("There is no \"on\" property, this workflow will never run")
//     }

//     println "We are handling a ${on.inspect()} ${workflow['on']}"
//     // Single event
//     if (String.isAssignableFrom(on.getClass())) {
//         return handles([on as String] as List, input)
//     }
//     // Multiple events
//     else if (Collection.isAssignableFrom(on.getClass())) {
//         return handles(on as List, input)
//     }
//     // Activity type
//     else if (Map.isAssignableFrom(on.getClass())) {
//         def workflowData1 = on.collect { key, value -> [key, value] }
//                 .find { it.get(0) == input['type'] }
//         if (workflowData1 == null) return false

//         def workflowData = workflowData1[1]

//         List<Boolean> votes = []

//         if (workflowData == null) {
//             return false
//         }

//         def branches = workflowData['branches']
//         if (branches != null && Collection.isAssignableFrom(branches.getClass())) {
//             def metadata = input['metadata']
//             if (metadata == null) return false
//             String branchName = metadata['branchName'] as String
//             if (branchName == null) {
//                 votes.add(false)
//             } else {
//                 Boolean vote = branches.findAll { it != null }
//                         .findAll { !(it as String).isEmpty() }
//                         .find { glob(it as String, branchName) } != null
//                 votes.add(vote)
//             }
//         }

//         def paths = workflowData['paths']
//         if (paths != null && Collection.isAssignableFrom(paths.getClass())) {
//             def additionalProperties = input['additionalProperties']
//             if (additionalProperties == null) return false
//             def files = additionalProperties['files'] as Map
//             if (files == null) {
//                 votes.add(false)
//             } else {
//                 def collect = paths.findAll { it != null }
//                         .findAll { !(it as String).isEmpty() }
//                         .collect { it ->
//                             files.collect { key, value -> key }
//                                     .collect { it2 ->
//                                         println it
//                                         println it2

//                                         return glob(it as String, it2 as String)
//                                     }
//                         }
//                         .flatten()
//                 votes.add(!collect.contains(false))
//             }
//         }

//         if (on['workflow-call'] != null || on['workflow-dispatch'] != null) {
//             votes.add(true)
//         }

//         return !votes.contains(false) && votes.size() > 0
//     }

//     throw new Exception("Should not happen ${on.getClass()}")
// }