import { useParams } from "react-router";

export const Workflow = () => {
    const { runId } = useParams();
    
    return <>Single workflow {runId}</>;
}