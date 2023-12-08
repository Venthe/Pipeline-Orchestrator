import fs from 'fs'
import path from 'path'

export const getFile = ({directory, filename, extensions}: {directory: string, filename: string, extensions?: string[]}) => {
    if (extensions && extensions?.length === 0) {
        return fs.readFileSync(path.join(directory, filename))
    }

    return extensions?.map(ext => path.join(directory, `${filename}.${ext}`))
        .map(path => ({ path, exists: fs.existsSync(path) }))
        .filter(obj => obj.exists)
        .map(obj => obj.path)
        .map(path => fs.readFileSync(path))[0]
}