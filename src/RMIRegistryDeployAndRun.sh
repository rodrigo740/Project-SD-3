source config
echo "Transfering data to the RMIregistry node."
sshpass -f password ssh $rmi_fname 'mkdir -p test/TheRestaurant'
sshpass -f password ssh $rmi_fname 'rm -rf test/TheRestaurant/*'
sshpass -f password ssh $rmi_fname 'mkdir -p Public/classes/interfaces'
sshpass -f password ssh $rmi_fname 'rm -rf Public/classes/interfaces/*'
sshpass -f password scp dirRMIRegistry.zip $rmi_fname:test/TheRestaurant
sshpass -f password scp config $rmi_fname:/home/sd201
echo "Decompressing data sent to the RMIregistry node."
sshpass -f password ssh $rmi_fname 'cd test/TheRestaurant ; unzip -uq dirRMIRegistry.zip'
sshpass -f password ssh $rmi_fname 'cd test/TheRestaurant/dirRMIRegistry ; cp interfaces/*.class /home/sd201/Public/classes/interfaces ; cp set_rmiregistry_d.sh /home/sd201'
echo "Executing program at the RMIregistry node."
sshpass -f password ssh $rmi_fname "./set_rmiregistry_d.sh sd201 $rmi_port"
