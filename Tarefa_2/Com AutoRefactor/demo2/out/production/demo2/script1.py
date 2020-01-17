#!/usr/bin/python3

import subprocess, sys, csv

loadRapl = []
registarUtilizador = []
registarVeiculo = []
iniciarSessao = []
get10ClientesKm = []

def prettyprint(name,arr):
  with open(sys.argv[1], mode='a') as test:
    test_writer = csv.writer(test, delimiter=';', quotechar='"', quoting=csv.QUOTE_MINIMAL)
    test_writer.writerow([name])
    for x in arr:
      test_writer.writerow(x.split(","))
    test_writer.writerow([])

if len(sys.argv) > 1:
  for x in range(0,10):
    bashCommand = 'java UmCarroJaApp' 
    process = subprocess.Popen(bashCommand.split(), stdout=subprocess.PIPE)
    output, error = process.communicate()
    utf8output = output.decode("utf-8").split("\n")
    loadRapl.append(utf8output[0])
    registarUtilizador.append(utf8output[1])
    registarVeiculo.append(utf8output[2])
    iniciarSessao.append(utf8output[3])
    get10ClientesKm.append(utf8output[4])

  prettyprint("Carregamento de ficheiro LOGS",loadRapl)
  prettyprint("Registar",registarUtilizador)
  prettyprint("Adicionar Carro",registarVeiculo)
  prettyprint("Login",iniciarSessao)
  prettyprint("Melhores Clientes em termos de km percorridos",get10ClientesKm)
  print(sys.argv[1]+" escrito com sucesso!")

else:
  print("Deve identificar o ficheiro de saida")
