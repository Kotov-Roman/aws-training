aws configure list



#copy from S3
aws s3 cp s3://roman-kotov-task1-bucket --recursive


aws s3 cp s3://roman-kotov-task1-bucket /home/ec2-user --recursive
cd /home/ec2-user || exit
java -jar app-1.0-SNAPSHOT-jar-with-dependencies.jar

#change access
chmod 777 project
#for key (only read)
chmod 400 linux3.pem

#swith to root
sudo su –

#work with httpd
sudo yum -y install httpd
sudo service httpd start
#root of httpd
cd /var/www/html/

sudo systemctl disable httpd

#62.4.34.145 ip last time
# sh .\task-3-auth-unix.sh <your-profile-name> <security-group-id> <region>
# sh update-my-ip-for-EC2-By-groupId.sh user_FullEC2 sg-02cc8f8dbe3b5af49 us-east-1
profile=$1
groupId=$2
region=$3
ipAddress=$(dig @resolver1.opendns.com ANY myip.opendns.com +short)
aws ec2 authorize-security-group-ingress --group-id $groupId --protocol tcp --port 80 --cidr $ipAddress/32 --profile $profile --region $region

#devices attached to the instance
sudo lsblk -f
#mkfs -t command to create a file system on the volume.
sudo mkfs -t xfs /dev/xvdr{or other volume name}
#create folder and link volume with it
sudo mkdir /data
sudo mount /dev/xvdr /data
sudo umount -d /dev/xvdr


sudo yum install java-11-amazon-corretto

ssh-keygen -t ed25519 -C "poma.kotov@gmail.com"
