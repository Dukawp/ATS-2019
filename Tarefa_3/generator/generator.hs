module Main ( main ) where

import Test.QuickCheck
import System.Environment

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

genProp :: Int -> [String] -> Gen [Prop]
genProp 0 _ = return []
genProp n b = do a <- vectorOf n $ genNome
                 --c <- vectorOf n $ genEmail 
                 d <- vectorOf n $ genMorada 
                 --return []
                 return (genProp' a b d)


--NovoProp: nome, nif, email,morada
genProp' :: [Nome] -> [Nif] -> [Morada] -> [Prop]
genProp' [] _ _ = []
genProp' (a:as) (b:bs) (d:ds) = [("NovoProp:"++a++","++b++","++b++"@gmail.com,"++d)]++(genProp' as bs ds)

genCliente :: Int -> [String] -> Gen [Cliente]
genCliente 0 _ = return []
genCliente n b = do a <- vectorOf n $ genNome
                    --c <- vectorOf n $ genEmail 
                    d <- vectorOf n $ genMorada 
                    x <- vectorOf n $ choose ((-9.32),(-6.32))
                    y <- vectorOf n $ choose ((37.0),(42.0))
                    return (genCliente' a b d x y)

--NovoCliente:nome,nif,email,morada,X,Y
genCliente' :: [Nome] -> [Nif] -> [Morada] -> [Float] -> [Float] -> [Cliente]
genCliente' [] _ _ _ _ = []
genCliente' (a:as) (b:bs) (d:ds) (x:xs) (y:ys) = [("NovoCliente:"++a++","++b++","++b++"@gmail.com,"++d++","++(show x)++","++(show y))]++(genCliente' as bs ds xs ys)

genTipo :: Gen String
genTipo = frequency [(9900,return "Gasolina"),(800,return "Hibrido"),(400,return "Electrico")]

genMarca :: Gen String
genMarca = frequency [(82,return "Abarth"),(363,return "Alfa Romeo"),(19,return "Aston Martion"),(2452,return "Audi"),(5042,return "BMW"),(160,return "Chevrolet"),(1724,return "Citroen"),(152, return "Dacia"),(115, return "DS"),(55, return "Ferrari"),(1948,return "Fiat"),(1645,return "Ford"),(323,return "Honda"),(392,return "Hyundai"),(4852,return "Mercedes")]

genMatricula :: Gen String
genMatricula = do a <- elements ['A'..'Z']
                  b <- elements ['A'..'Z']
                  c <- elements ['0'..'9']
                  d <- elements ['0'..'9']
                  e <- elements ['0'..'9']
                  f <- elements ['0'..'9']
                  frequency [(80,return (c:d:'-':a:b:'-':e:f:[])),(15,return (c:d:'-':e:f:'-':a:b:[])),(5,return (a:b:'-':c:d:'-':e:f:[]))]

genVelocidade :: Gen Int
genVelocidade = elements [40..90]

genPreco :: Gen Float
genPreco = choose ((1.2001),3)

genConsumo :: Gen Float
genConsumo = choose (1.0000,4)

genAutonomia :: Gen Int
genAutonomia = frequency [(70,(elements [450..600])),(10,(elements [600..900])),(20,(elements [350..450]))]

--NovoCarro:tipo,marca,matricula,nif,velocidade media,preço por km, consumo por km, autonomia, X, Y
genCarro :: Int -> [String] -> [String] -> Gen [String]
genCarro 0 _ _ = return []
genCarro n c nifs = do a <- vectorOf n $ genTipo
                       b <- vectorOf n $ genMarca
                       d <- shuffle $ multiplyList ((n `quot` (length nifs))+1) nifs
                       e <- vectorOf n $ genVelocidade
                       f <- vectorOf n $ genPreco
                       g <- vectorOf n $ genConsumo
                       h <- vectorOf n $ genAutonomia
                       x <- vectorOf n $ choose ((-9.32),(-6.32))
                       y <- vectorOf n $ choose ((37.0),(42.0))
                       return (genCarro' a b c d e f g h x y)

getNif :: [String] -> Gen String
getNif n = elements n

genCarro' :: [String] -> [String] -> [String] -> [String] -> [Int] -> [Float] -> [Float] -> [Int] -> [Float] -> [Float] -> [String]
genCarro' [] _ _ _ _ _ _ _ _ _ = []
genCarro' (a:as) (b:bs) (c:cs) (d:ds) (e:es) (f:fs) (g:gs) (h:hs) (x:xs) (y:ys) = [("NovoCarro:"++a++","++b++","++c++","++d++","++(show e)++","++(show f)++","++(show g)++","++(show h)++","++(show x)++","++(show y))]++(genCarro' as bs cs ds es fs gs hs xs ys)
--Aluguer: nif cliente, X destino, Y destino, tipoCombustivel , preferencia
genPerferencia :: Gen String
genPerferencia = elements ["MaisPerto","MaisBarato"]

genAluguer :: Int -> [String] -> Gen [String]
genAluguer 0 _ = return []
genAluguer n nifs = do a <- shuffle $ multiplyList ((n `quot` (length nifs))+1) nifs                   
                       x <- vectorOf n $ choose ((-9.32),(-6.32))
                       y <- vectorOf n $ choose ((37.0),(42.0))
                       b <- vectorOf n $ genTipo
                       c <- vectorOf n $ genPerferencia
                       return (genAluguer' a x y b c)

genAluguer' :: [String] -> [Float] -> [Float] -> [String] -> [String] -> [String]
genAluguer' _ [] _ _ _ = []
genAluguer' (a:as) (x:xs) (y:ys) (b:bs) (c:cs) = [("Aluguer:"++a++","++(show x)++","++(show y)++","++b++","++c)]++(genAluguer' as xs ys bs cs)

--Classificar: matricula ou nif (cliente ou prop) , nota (0-100)
genNota :: Gen Int
genNota = frequency [(80,(elements [50..80])),(1,(elements [0..40])),(4,(elements [41..49])),(15,(elements [81..100]))]

genClassificar :: Int -> [String] -> [String] -> Gen [String]
genClassificar 0 _ _ = return []
genClassificar n mat nifs = do a <- shuffle (mat++nifs)
                               b <- vectorOf n $ genNota
                               return (genClassificar' a b)

genClassificar' :: [String] -> [Int] -> [String]
genClassificar' _ [] = []
genClassificar' (a:as) (b:bs) = ["Classificar:"++a++","++(show b)]++(genClassificar' as bs)

--genLogs :: Int -> Gen [String]
--genLogs n = do nifs <- vectorOf n $ genNif 

main = do args <- getArgs
          let arg1 = head args
          let n = (read arg1) :: Int
          nifProps <- generate $ vectorOf (200*n) $ genNif 
          nifClientes <- generate $ vectorOf (600*n) $ genNif
          props <- generate $ genProp (200*n) nifProps
          clients <- generate $ genCliente (600*n) nifClientes
          matriculas <- generate $ vectorOf (2000*n) $ genMatricula
          cars <- generate $ genCarro (2000*n) matriculas nifProps
          alu <- generate $ genAluguer (500*n) nifClientes
          clas <- generate $ genClassificar (2000*n) matriculas nifProps
          mapM_ putStrLn props
          mapM_ putStrLn clients
          mapM_ putStrLn cars
          mapM_ putStrLn alu
          mapM_ putStrLn clas

multiplyList :: Int -> [a] -> [a] 
multiplyList 0 list = []
multiplyList n list = list++(multiplyList (n-1) list)

delete element list = filter (\e -> e/=element) list


--Unsafe preform IO

