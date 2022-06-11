source config
xterm  -T "RMI registry" -hold -e "./RMIRegistryDeployAndRun.sh" &
sleep 4
xterm  -T "Registry" -hold -e "./RegistryDeployAndRun.sh" &
sleep 4
xterm  -T "General Repository" -hold -e "./GeneralReposDeployAndRun.sh" &
sleep 4
xterm  -T "Bar" -hold -e "./BarDeployAndRun.sh" &
xterm  -T "Table" -hold -e "./TableDeployAndRun.sh" &
xterm  -T "Kitchen" -hold -e "./KitchenDeployAndRun.sh" &
sleep 4
xterm  -T "Waiter" -hold -e "./WaiterDeployAndRun.sh" &
xterm  -T "Chef" -hold -e "./ChefDeployAndRun.sh" &
xterm  -T "Students" -hold -e "./StudentsDeployAndRun.sh" &
