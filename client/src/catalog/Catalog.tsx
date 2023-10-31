import {
    Button,
    Dropdown,
    DropdownProps,
    Option,
    Tag,
    TagGroup,
    makeStyles,
    shorthands,
    useId
} from "@fluentui/react-components";
import { CatalogService, Component } from "./catalog.service";
import { useState } from "react";
import {
    DataGridBody,
    DataGridRow,
    DataGrid,
    DataGridHeader,
    DataGridHeaderCell,
    DataGridCell,
    TableCellLayout,
    TableColumnDefinition,
    createTableColumn,
} from "@fluentui/react-components";
import {
    bundleIcon,
    BoxEditFilled,
    BoxEditRegular,
    BookOpenFilled,
    BookOpenRegular,
    CheckmarkStarburstFilled,
    CheckmarkStarburstRegular
} from "@fluentui/react-icons";
import { Link } from "react-router-dom";

const dropdownStyles = makeStyles({
    root: {
        // Stack the label above the field with a gap
        display: "grid",
        gridTemplateRows: "repeat(1fr)",
        justifyItems: "start",
        ...shorthands.gap("2px"),
        maxWidth: "400px",
    },
});

const PlainDropdown = ({ options, ...props }: Partial<DropdownProps> & { id: string, options: string[], onSelect?: (value: string) => void }) => {
    const dropdownId = useId(props.id);
    const styles = dropdownStyles()
    return (
        <div className={styles.root}>
            <label id={dropdownId}>Kind</label>
            <Dropdown
                onOptionSelect={(e, v) => { if (v.optionValue) props.onSelect?.(v.optionValue) }}
                aria-labelledby={dropdownId}
                {...props}>
                {options.map((option) => (
                    <Option key={option}>
                        {option}
                    </Option>
                ))}
            </Dropdown>
        </div>
    )
}

const KindDropdown = (props: Partial<DropdownProps> & { options: string[], onSelect?: (value: string) => void }) =>
    <PlainDropdown id={"kind-dropdown"} {...props} />

const TypeDropdown = (props: Partial<DropdownProps> & { options: string[], onSelect?: (value: string) => void }) =>
    <PlainDropdown id={"type-dropdown"} {...props} />

const ViewIcon = bundleIcon(BookOpenFilled, BookOpenRegular);
const EditIcon = bundleIcon(BoxEditFilled, BoxEditRegular);
const AddToFavouritesIcon = bundleIcon(CheckmarkStarburstFilled, CheckmarkStarburstRegular);

const ComponentGrid = (props: { items: Component[] }) => {
    const columns: TableColumnDefinition<Component>[] = [
        createTableColumn<Component>({
            columnId: "id",
            renderHeaderCell: () => "Name",
            //compare: (a, b) => a.lastUpdated.timestamp - b.lastUpdated.timestamp,
            renderCell: (item) => {
                const organizationUrlPrefix = item.organization ?? "default"
                const addOrganizationToName = (name) => item.organization ? `${item.organization}/${name}` : name
                return (<TableCellLayout><Link to={`${organizationUrlPrefix}/component/${item.id}`}>{addOrganizationToName(item.id)}</Link></TableCellLayout>);
            },
        }),
        createTableColumn<Component>({
            columnId: "system",
            renderHeaderCell: () => "System",
            //compare: (a, b) => a.lastUpdated.timestamp - b.lastUpdated.timestamp,
            renderCell: (item) => (<TableCellLayout><a href="#">{item.system}</a></TableCellLayout>),
        }),
        createTableColumn<Component>({
            columnId: "owner",
            renderHeaderCell: () => "Owner",
            //compare: (a, b) => a.lastUpdated.timestamp - b.lastUpdated.timestamp,
            renderCell: (item) => (<TableCellLayout><a href="#">{item.owner}</a></TableCellLayout>),
        }),
        createTableColumn<Component>({
            columnId: "type",
            renderHeaderCell: () => "Type",
            //compare: (a, b) => a.lastUpdated.timestamp - b.lastUpdated.timestamp,
            renderCell: (item) => (<TableCellLayout>{item.type}</TableCellLayout>),
        }),
        createTableColumn<Component>({
            columnId: "lifecycle",
            renderHeaderCell: () => "Lifecycle",
            //compare: (a, b) => a.lastUpdated.timestamp - b.lastUpdated.timestamp,
            renderCell: (item) => (<TableCellLayout>{item.lifecycle}</TableCellLayout>),
        }),
        createTableColumn<Component>({
            columnId: "description",
            renderHeaderCell: () => "Description",
            //compare: (a, b) => a.lastUpdated.timestamp - b.lastUpdated.timestamp,
            renderCell: (item) => (<TableCellLayout>{item.description}</TableCellLayout>),
        }),
        createTableColumn<Component>({
            columnId: "tags",
            renderHeaderCell: () => "Tags",
            //compare: (a, b) => a.lastUpdated.timestamp - b.lastUpdated.timestamp,
            renderCell: (item) => (
                <TableCellLayout>
                    <TagGroup aria-label="Component tags">
                        {item.tags.map(tag => <Tag role="listitem">{tag}</Tag>)}
                    </TagGroup>
                </TableCellLayout>
            ),
        }),
        createTableColumn<Component>({
            columnId: "actions",
            renderHeaderCell: () => "Actions",
            //compare: (a, b) => a.lastUpdated.timestamp - b.lastUpdated.timestamp,
            renderCell: (item) =>
            (<TableCellLayout>
                {/* view */}
                <Button icon={<ViewIcon />} />
                {/* edit */}
                <Button icon={<EditIcon />} />
                {/* add to favourites */}
                <Button icon={<AddToFavouritesIcon />} />
            </TableCellLayout>),
        })
    ];

    return (
        <DataGrid
            items={props.items}
            columns={columns}
            sortable
            selectionMode="multiselect"
            getRowId={(item) => item.id}
            onSelectionChange={(e, data) => console.log(data)}
            focusMode="composite">
            <DataGridHeader>
                <DataGridRow selectionCell={{ "aria-label": "Select all rows" }}>
                    {({ renderHeaderCell }) => (
                        <DataGridHeaderCell>{renderHeaderCell()}</DataGridHeaderCell>
                    )}
                </DataGridRow>
            </DataGridHeader>
            <DataGridBody<Component>>
                {({ item, rowId }) => (
                    <DataGridRow<Component>
                        key={rowId}
                        selectionCell={{ "aria-label": "Select row" }}>
                        {({ renderCell }) => (
                            <DataGridCell>{renderCell(item)}</DataGridCell>
                        )}
                    </DataGridRow>
                )}
            </DataGridBody>
        </DataGrid>
    );
}

export const Catalog = () => {
    const catalogService = new CatalogService();

    const catalogElements = catalogService.getCatalogElement()
    const [selectedKind, setSelectedKind] = useState<undefined | string>(undefined)
    const types = catalogElements.filter(catalogElement => catalogElement.kind === selectedKind).flatMap(catalogElement => catalogElement.types ?? [])

    const components = catalogService.getComponents();
    return (
        <div style={{ display: "grid", gridTemplateColumns: "30% 1fr" }}>
            <div>
                <KindDropdown options={catalogElements.map(a => a.kind)} onSelect={kind => setSelectedKind(kind)} />
                {types.length > 0 ? <TypeDropdown options={types.map(e => e.key)} /> : <></>}
            </div>
            <div>
                <ComponentGrid items={components} />
            </div>
        </div>
    );
}