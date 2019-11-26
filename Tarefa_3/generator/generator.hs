import Test.QuickCheck

type Nome = String
type Email = String
type Nif = Int
type Morada = String

--nomesProprios = ["João","Maria","Francisco","Leonor","Santiago","Matilde","Afonso","Carolina","Duarte","Beatriz","Tomás","Sofia","Martim","Alice","Rodrigo","Mariana","Lourenço","Ana","Gabriel","Benedita","Miguel","Francisca","Lucas","Margarida","Pedro","Inês","Vicente","Clara","Guilherme","Lara","Dinis","Laura","Salvador","Madalena","Gonçalo","Diana","Rafael","Joana","Gustavo","Eva"]
--nomesFamilia = ["Silva","Santos","Ferreira","Pereira","Oliveira","Costa","Rodrigue","Martins","Jesus","Sousa","Fernande","Gonçalve","Gomes","Lopes","Marques","Alves","Almeida","Ribeiro","Pinto","Carvalho","Teixeira","Moreira","Correia","Mendes","Nunes","Soares","Vieira","Monteiro","Cardoso","Rocha","Raposo","Neves","Coelho","Cruz","Cunha","Pires","Ramos","Reis","Simões","Antunes"]


genNome = do a <- elements ["João","Maria","Francisco","Leonor","Santiago","Matilde","Afonso","Carolina","Duarte","Beatriz","Tomás","Sofia","Martim","Alice","Rodrigo","Mariana","Lourenço","Ana","Gabriel","Benedita","Miguel","Francisca","Lucas","Margarida","Pedro","Inês","Vicente","Clara","Guilherme","Lara","Dinis","Laura","Salvador","Madalena","Gonçalo","Diana","Rafael","Joana","Gustavo","Eva"]
             b <- elements ["Silva","Santos","Ferreira","Pereira","Oliveira","Costa","Rodrigue","Martins","Jesus","Sousa","Fernande","Gonçalve","Gomes","Lopes","Marques","Alves","Almeida","Ribeiro","Pinto","Carvalho","Teixeira","Moreira","Correia","Mendes","Nunes","Soares","Vieira","Monteiro","Cardoso","Rocha","Raposo","Neves","Coelho","Cruz","Cunha","Pires","Ramos","Reis","Simões","Antunes"]
          return (a ++ " " ++ b)
