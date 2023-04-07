#scp cool_stuff.txt sanjeev@example.com:.    will upload to home/ec2-user/
#ssh -i "/Users/Roman_Kotov/Downloads/linux3.pem" ec2-user@54.236.213.133
scp -i "/Users/Roman_Kotov/IdeaProjects/aws-related/aws-commands/keys/linux4.pem" app-1.0-SNAPSHOT-jar-with-dependencies.jar ec2-user@54.159.0.158:/home/ec2-user
