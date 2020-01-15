#!/usr/bin/python3

import subprocess, sys, csv

loadRapl = []
bestClients = []
regist = []
addCar = []
login = []
bestClientsTimes = []
bestClientsTravel = []

def prettyprint(name,arr):
  with open(sys.argv[1], mode='a') as test:
    test_writer = csv.writer(test, delimiter=';', quotechar='"', quoting=csv.QUOTE_MINIMAL)
    test_writer.writerow([name])
    for x in arr:
      test_writer.writerow(x.split(","))
    test_writer.writerow([])

if len(sys.argv) > 1:
  for x in range(0,10):
    bashCommand = 'python3 test.py' 
    process = subprocess.Popen(bashCommand.split(), stdout=subprocess.PIPE)
    output, error = process.communicate()
    utf8output = output.decode("utf-8").split("\n")
    loadRapl.append(utf8output[0])
    bestClients.append(utf8output[1])
    regist.append(utf8output[2])
    addCar.append(utf8output[3])
    login.append(utf8output[4])
    bestClientsTimes.append(utf8output[5])
    bestClientsTravel.append(utf8output[6])

  prettyprint("Carregamento de ficheiro LOGS",loadRapl)
  prettyprint("Melhores Clientes",bestClients)
  prettyprint("Registar",refil)
  prettyprint("Adicionar Carro",addCar)
  prettyprint("Login",login)
  prettyprint("Tempos de melhores clientes",bestClientsTimes)
  prettyprint("Viagens de melhores clientes",bestClientsTravel)
  print(sys.argv[1]+" escrito com sucesso!")

else:
  print("Deve identificar o ficheiro de saida")
