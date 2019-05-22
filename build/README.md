# build

Dockerfile and neccessary files to build a custom dokcer image.

- copy your groovy scripts and Jenkins pipeline files under `groovy` and `pipelines` folder.
- update [plugins list](plugins.txt) if you added or removed any plugin.

Run [groovy script](../jenkins/groovy-scripts/get-jenkins-plugin-list.groovy) in Jenkins Script console to see latest installed plugin list.

- build docker image under `build folder`

```bash
/build$ docker build -t <image>:<tag> .
```
