import Test.QuickCheck

type Nome = String
type Email = String
type Nif = String
type Morada = String
type Prop = String
type Cliente = String

nomesProprios = ["Joao","Maria","Francisco","Leonor","Santiago","Matilde","Afonso","Carolina","Duarte","Beatriz","Tomas","Sofia","Martim","Alice","Rodrigo","Mariana","Lourenco","Ana","Gabriel","Benedita","Miguel","Francisca","Lucas","Margarida","Pedro","Inês","Vicente","Clara","Guilherme","Lara","Dinis","Laura","Salvador","Madalena","Goncalo","Diana","Rafael","Joana","Gustavo","Eva"]
nomesFamilia = ["Silva","Santos","Ferreira","Pereira","Oliveira","Costa","Rodrigue","Martins","Jesus","Sousa","Fernande","Goncalve","Gomes","Lopes","Marques","Alves","Almeida","Ribeiro","Pinto","Carvalho","Teixeira","Moreira","Correia","Mendes","Nunes","Soares","Vieira","Monteiro","Cardoso","Rocha","Raposo","Neves","Coelho","Cruz","Cunha","Pires","Ramos","Reis","Simoes","Antunes"]
moradas = ["Amares","Barcelos","Braga","Cabeceiras de Basto","Celorico de Basto","Esposende","Fafe","Guimaraes","Povoa de Lanhoso","Terras do Bouro","Vieira do Minho","Vila Nova de Famalicao","Vila Verde","Vizela","Arcos de Valdevez","Caminha","Melgaco","Moncao","Paredes de Coura","Ponte da Barca","Ponte do Lima","Valenca","Viana do Castelo","Vila Nova de Cerveira"]

genNome :: Gen Nome
<<<<<<< HEAD
genNome = do a <- elements nomesProprios
             b <- elements nomesFamilia
             return (a++" "++b)

genNumber :: Int -> Gen Nif
genNumber 0 = return ""
genNumber n = do a <- elements [0..9]
                 b <- genNumber (n-1)
                 return ((show a)++b)

genNif :: Gen Nif
genNif = genNumber 9

genPrefix' :: Int -> Gen Email
genPrefix' 0 = return []
genPrefix' n = do a <- elements ['0'..'9']
                  b <- elements ['a'..'z']
                  c <- frequency [(73,return b),(27,return a)]
                  d <- genPrefix' (n-1)
                  return (c:d)

genEmail :: Gen Email
genEmail = do a <- genPrefix' 8
              return (a++"@umcarro.pt")

genMorada :: Gen Morada
genMorada = elements moradas

genProp :: Int -> Gen [Prop]
genProp 0 = return []
genProp n = do a <- vectorOf n $ genNome
               b <- vectorOf n $ genNif
               c <- vectorOf n $ genEmail 
               d <- vectorOf n $ genMorada 
               --return []
               return (genProp' a b c d)


--NovoProp: nome, nif, email,morada
genProp' :: [Nome] -> [Nif] -> [Email] -> [Morada] -> [Prop]
genProp' [] _ _ _ = []
genProp' (a:as) (b:bs) (c:cs) (d:ds) = [("NovoProp:"++a++":"++b++":"++c++":"++d)]++(genProp' as bs cs ds)

genCliente :: Int -> Gen [Cliente]
genCliente 0 = return []
genCliente n = do a <- vectorOf n $ genNome
                  b <- vectorOf n $ genNif
                  c <- vectorOf n $ genEmail 
                  d <- vectorOf n $ genMorada 
                  x <- vectorOf n $ choose ((-9.32),(-6.32))
                  y <- vectorOf n $ choose ((37.0),(42.0))
                  return (genCliente' a b c d x y)

--NovoCliente:nome,nif,email,morada,X,Y
genCliente' :: [Nome] -> [Nif] -> [Email] -> [Morada] -> [Float] -> [Float] -> [Cliente]
genCliente' [] _ _ _ _ _ = []
genCliente' (a:as) (b:bs) (c:cs) (d:ds) (x:xs) (y:ys) = [("NovoCliente:"++a++":"++b++":"++c++":"++d++":"++(show x)++":"++(show y))]++(genCliente' as bs cs ds xs ys)

--NovoCarro:tipo,marca,matricula,nif,velocidade media,preço por km, consumo por km, autonomia, X, Y
genTipo :: Gen String
genTipo = frequency [(100, return "Combustao"),(50,"Gasolina"),(20,"Hibrido"),(15,"Eletrico")]

genMarca :: Gen String
genMarca = frequency [(120,return "Renault"),(85,return "Mercedes"),(12,return "Porsche"),(4,return "Ferrari")]

genAutonomia :: Gen Autonomia
genAutonomia = elements [450..600]


--Aluguer: nif cliente, X destino, Y destino, tipoCombustivel , preferencia

--Classificar: matricula ou nif (cliente ou prop) , nota (0-100)

delete element list = filter (\e -> e/=element) list


--Unsafe preform IO

