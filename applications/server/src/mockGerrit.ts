import { GerritEventSnapshot } from "@pipeline/types"
import dedent from "dedent"

const p = (data: string): Promise<string> => new Promise((resolve) => {resolve(data)})

export const showFile = async (props: { projectName: string, ref: string, filePath: string, request?: Partial<Request>, format?: 'TEXT'}): Promise<string> => {
   switch (props.filePath) {
    default:
        return p(dedent`
        name: Echo
        on: [push]
        jobs:
            Explore-GitHub-Actions:
                runs-on: docker.home.arpa/venthe/ubuntu-runner:22.10
                steps:
                - run: echo "🎉 The job was automatically triggered by a \${EVENT_NAME} event."
                - run: ls
        `)
   }
}

export const getEvent = (): GerritEventSnapshot & any => {
    return {
        "uploader": {
          "name": "Administrator",
          "email": "admin@example.com",
          "username": "admin"
        },
        "patchSet": {
          "number": 7,
          "revision": "96181cf7e9e8a555872283bf88fbe80d95507fb6",
          "parents": [
            "9a61085f1faaf54a57104868d44e53f3b4572cf5"
          ],
          "ref": "refs/changes/02/2/7",
          "uploader": {
            "name": "Administrator",
            "email": "admin@example.com",
            "username": "admin"
          },
          "createdOn": 1671184701,
          "author": {
            "name": "Administrator",
            "email": "admin@example.com",
            "username": "admin"
          },
          "kind": "REWORK",
          "sizeInsertions": 11,
          "sizeDeletions": 0
        },
        "change": {
          "project": "All-Projects",
          "branch": "refs/meta/config",
          "topic": "topic",
          "id": "Ida887840d4b8e20d3570fcce7a00c1f72cd0d82b",
          "number": 2,
          "subject": "description",
          "owner": {
            "name": "Administrator",
            "email": "admin@example.com",
            "username": "admin"
          },
          "url": "http://gerrit/c/All-Projects/+/2",
          "commitMessage": "description\n\nChange-Id: Ida887840d4b8e20d3570fcce7a00c1f72cd0d82b\n",
          "createdOn": 1671182081,
          "status": "NEW",
          "wip": true
        },
        "project": {
          "name": "All-Projects"
        },
        "refName": "refs/meta/config",
        "changeKey": {
          "key": "Ida887840d4b8e20d3570fcce7a00c1f72cd0d82b"
        },
        "type": "patchset-created",
        "eventCreatedOn": 1671184701
      }
}