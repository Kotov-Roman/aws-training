# sh .\task-3-auth-unix.sh <your-profile-name> <security-group-id> <region>
# sh update-my-ip-for-EC2-By-groupId.sh user_FullEC2 sg-02cc8f8dbe3b5af49 us-east-1
profile=$1
groupId=$2
region=$3
ipAddress=$(dig @resolver1.opendns.com ANY myip.opendns.com +short)
aws ec2 authorize-security-group-ingress --group-id $groupId --protocol tcp --port 80 --cidr $ipAddress/32 --profile $profile --region $region
