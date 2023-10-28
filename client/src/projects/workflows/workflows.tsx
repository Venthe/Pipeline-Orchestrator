import { DataGrid, DataGridBody, DataGridCell, DataGridCellFocusMode, DataGridHeader, DataGridHeaderCell, DataGridRow, TableColumnDefinition, TableColumnId, createTableColumn } from "@fluentui/react-table";
import { WorkflowRun, WorkflowsService } from "./workflows.service";
import { Menu, MenuItem, MenuList, MenuPopover, MenuTrigger } from "@fluentui/react-menu";
import { MenuButton } from "@fluentui/react-button";
import { Link } from "react-router-dom";

const columns: TableColumnDefinition<WorkflowRun>[] = [
    createTableColumn<WorkflowRun>({
        columnId: "status",
        compare: (a, b) => a.status.localeCompare(b.status),
        renderHeaderCell: () => <span>Status</span>,
        renderCell: (item) => <>{item.status}</>
    }),
    createTableColumn<WorkflowRun>({
        columnId: "label",
        compare: (a, b) => a.label.localeCompare(b.label),
        renderHeaderCell: () => <span>Label</span>,
        renderCell: (item) => <Link to={"runs/" + item.runId}>{item.label}</Link>
    }),
    createTableColumn<WorkflowRun>({
        columnId: "startDate",
        compare: (a, b) => a.startDate.valueOf() - b.startDate.valueOf(),
        renderHeaderCell: () => <span>Starting date</span>,
        renderCell: (item) => <pre>{JSON.stringify(item.startDate, undefined, 2)}</pre>
    }),
    createTableColumn<WorkflowRun>({
        columnId: "actions",
        renderHeaderCell: () => <span>Actions</span>,
        renderCell: (item) => (
            <Menu>
                <MenuTrigger disableButtonEnhancement>
                    <MenuButton>View workflow file</MenuButton>
                </MenuTrigger>

                <MenuPopover>
                    <MenuList>
                        <MenuItem>Delete run</MenuItem>
                    </MenuList>
                </MenuPopover>
            </Menu>
        )
    })
]

const getCellFocusMode = (columnId: TableColumnId): DataGridCellFocusMode => {
    switch (columnId) {
        case "singleAction":
            return "none";
        case "actions":
            return "group";
        default:
            return "cell";
    }
};

export const Workflows = () => {
    const workflowsService = new WorkflowsService();

    const workflows = workflowsService.listRuns()

    return <>
        <DataGrid items={workflows} columns={columns} sortable>
            <DataGridHeader>
                <DataGridRow>
                    {({ renderHeaderCell }) => (
                        <DataGridHeaderCell>{renderHeaderCell()}</DataGridHeaderCell>
                    )}
                </DataGridRow>
            </DataGridHeader>
            <DataGridBody<WorkflowRun>>
                {({ item, rowId }) => (
                    <DataGridRow<WorkflowRun>
                        key={rowId}
                    >
                        {({ renderCell, columnId }) => (
                            <DataGridCell focusMode={getCellFocusMode(columnId)}>
                                {renderCell(item)}
                            </DataGridCell>
                        )}
                    </DataGridRow>
                )}
            </DataGridBody>
        </DataGrid>
    </>
}