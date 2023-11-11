import express from 'express'
import git from 'git'
import { inTempDirectory } from './temp'

inTempDirectory(async (dir) => {
  return new Promise((resolve, reject) => {
    new git.Remote(dir, (err, repo) => {
      console.error("!!!")
      console.error("!", err)
      console.debug("!", repo)
      resolve()
    })
  })
})

// const app = express()
// const port = 3000

// app.get('/', (req, res) => {
//   res.send('Hello World!!!!')
// })

// app.listen(port, async () => {
//   console.log(`Example app listening on port ${port}`)
// })