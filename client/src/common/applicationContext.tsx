import React from "react";
import { FluentProvider, webDarkTheme } from '@fluentui/react-components';

export const ApplicationContext = ({ children }) => (
    <>
        <React.StrictMode>
            <FluentProvider theme={webDarkTheme}>
                {children}
            </FluentProvider>
        </React.StrictMode>
    </>
)