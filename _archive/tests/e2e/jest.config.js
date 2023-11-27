const { defaults } = require('jest-config');

/** @returns {Promise<import('jest').Config>} */
module.exports = async () => {
    return {
        testEnvironment: "node",
        verbose: true,
        moduleFileExtensions: [...defaults.moduleFileExtensions, 'mts', 'cts', 'ts'],
        testMatch: [
            "**/*.steps.ts"
        ],
        transform: {
            '^.+\\.m?[tj]sx?$': [
                'ts-jest',
                {
                    tsconfig: 'tsconfig.json',
                },
            ],
        }
    };
};