git clone http://admin:FZl81zxDM9+TlPhkwREaKGJikfwCTjW8NTqi+3mgVQ@localhost:8080/a/All-Projects
git fetch origin refs/meta/config:refs/remotes/origin/meta/config
git checkout meta/config
touch webhooks.config

[remote "assignee-changed"]
  event = assignee-changed
  url = http://host.docker.internal:8084
[remote "change-abandoned"]
  event = change-abandoned
  url = http://host.docker.internal:8084
[remote "change-deleted"]
  event = change-deleted
  url = http://host.docker.internal:8084
[remote "change-merged"]
  event = change-merged
  url = http://host.docker.internal:8084
[remote "change-restored"]
  event = change-restored
  url = http://host.docker.internal:8084
[remote "comment-added"]
  event = comment-added
  url = http://host.docker.internal:8084
[remote "dropped-output"]
  event = dropped-output
  url = http://host.docker.internal:8084
[remote "project-created"]
  event = project-created
  url = http://host.docker.internal:8084
[remote "patchset-created"]
  event = patchset-created
  url = http://host.docker.internal:8084
[remote "ref-updated"]
  event = ref-updated
  url = http://host.docker.internal:8084
[remote "reviewer-added"]
  event = reviewer-added
  url = http://host.docker.internal:8084
[remote "reviewer-deleted"]
  event = reviewer-deleted
  url = http://host.docker.internal:8084
[remote "topic-changed"]
  event = topic-changed
  url = http://host.docker.internal:8084
[remote "wip-state-changed"]
  event = wip-state-changed
  url = http://host.docker.internal:8084
[remote "private-state-changed"]
  event = private-state-changed
  url = http://host.docker.internal:8084
[remote "vote-deleted"]
  event = vote-deleted
  url = http://host.docker.internal:8084
[remote "project-head-updated"]
  event = project-head-updated
  url = http://host.docker.internal:8084


git push origin meta/config:refs/meta/config