#this filters by date and after pipe (|) takes first element  --query 'Versions[?LastModified>=`2023-03-23`]|[0]'
#aws s3api list-object-versions --bucket roman-kotov-task2-bucket  --query 'Versions[?LastModified>=`2023-03-23`]|[0]' --prefix css --profile user_ReadS3
#aws s3api list-objects --bucket $1  --profile user_ReadS3 roman-kotov-task1-bucket
#sh list-objects.sh roman-kotov-task1-bucket user_ReadS3
aws s3api list-objects --bucket $1 --profile $2
