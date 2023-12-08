import { graph } from "./commands";

const grunt = require("grunt");
const path = require('path');
const { program } = require('commander');

program.command("graph").action(async () => {
    await graph()
})

// program
//   .option('-d, --debug', 'output extra debugging')

// program.parse(process.argv);

// const options = program.opts();
// if (options.debug) console.log(options);

// grunt.cli({
//     gruntfile: path.join(__dirname, "gruntfile.js"),
//     extra: { key: "value" }
// });

program.parse();