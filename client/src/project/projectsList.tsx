// import {ProjectsService} from "./projects.service"
// import { Link } from "react-router-dom";

// export const ProjectsList = () =>{
//     const projectsService = new ProjectsService();

//     const toTable = () => projectsService.getProjects().map(e => <div><Link to={e.id}>{e.id}</Link><pre>{JSON.stringify(e, undefined, 2)}</pre></div>)

//     return (
//     <><pre>{toTable()}</pre></>
//     )
// }