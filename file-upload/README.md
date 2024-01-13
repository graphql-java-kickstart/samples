# File Upload
This Spring example shows how the upload operation works using GraphQL and the Apollo Upload type.

## How to run
- Create a file: `echo 'Hello World!' > hello-world.txt`
- Run the example: `../gradlew bootRun`
- Make a GraphQL request:
```sh
curl --location 'http://localhost:9000/graphql' \
     --form 'operations="{\"query\": \"mutation upload($file:Upload){ upload(file: $file){filename type content}}\"}"' \
     --form 'map="{\"blob\": [\"variables.file\"]}"' \
     --form 'blob=@"./hello-world.txt"'
```

If the file is successfully uploaded, this is the response received and the log shown:
```json
{
  "data": {
    "upload": {
      "filename": "hello-world.txt",
      "type": "text/plain",
      "content": "Hello World!\n"
    }
  }
}

```

```log
INFO 1584868 --- [nio-9000-exec-7] upload.UploadMutation: File uploaded: {type=text/plain, filename=hello-world.txt, content=Hello World!}
```
