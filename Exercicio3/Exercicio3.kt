abstract class Conta(
  val titular: String,
  val numero: Int
) {

  var saldo: Double = 0.0
  protected set
  protected val historico = mutableListOf<String>()

  open fun deposita(valor: Double) {
    if (valor > 0) {

      saldo += valor
      historico.add("Depósito: R$ $valor")
      println("Depósito realizado com sucesso!")

    } else {

      println("Depósito falhou!")
    }
  }

  open fun sacar(valor: Double): Boolean {
    if (valor > 0 && saldo >= valor) {

      saldo -= valor
      historico.add("Saque: -R$ $valor")
      println("Saque realizado com sucesso!")
      return true

    } else {

      if(valor > saldo){
        println("Saldo insuficiente para realizar esta operação!")

} else {

        println("Depósito falhou!")
      }
      return false
    }
  }



  fun exibeHistorico() {

    println("\n===================================\nHistórico da Conta $numero ($titular)\n===================================\n")

    if (historico.isEmpty()) {
      println("Nenhuma operação registrada.")

    } else {
      historico.forEach { println("- $it") }

    }

    println("----------------------------------------------")

  }

  abstract fun exibeDados()
}


class ContaCorrente(titular: String, numero: Int, val taxaSaque: Double = 2.50) : Conta(titular, numero) {

  override fun sacar(valor: Double): Boolean {
    val valorTotalComTaxa = valor + taxaSaque

    if (valor > 0 && saldo >= valorTotalComTaxa) {

      saldo -= valorTotalComTaxa
      historico.add("Saque (c/ taxa R$ $taxaSaque): -R$ $valor (Total debitado: R$ $valorTotalComTaxa)")
      println("Saque realizado com sucesso!")

      return true

    } else {

      println("Depósito falhou!")
      return false

    }

  }



  override fun exibeDados() {

    println("\n[Tipo: Conta Corrente]")
    println("Cliente: $titular\nConta: $numero\nSaldo Atual: R$ $saldo\nTaxa de Saque: R$ $taxaSaque")

  }

}



class ContaPoupanca(titular: String, numero: Int, val taxaRendimento: Double = 0.01) : Conta(titular, numero) {

  fun aplicaRendimento() {
    val rendimento = saldo * taxaRendimento
    saldo += rendimento
    historico.add("Rendimento aplicado: +R$ $rendimento")
    println("Aplicando taxa mensal...")

  }



  override fun exibeDados() {

    println("\n[Tipo: Conta Poupança]")
    println("Cliente: $titular\nConta: $numero\nSaldo Atual: R$ $saldo\nRendimento: ${taxaRendimento * 100}%")

  }

}



fun main() {

  println("=== BEM-VINDO AO BYTEBANK EVOLUTION ===")

  // 1. Criação de Múltiplas Contas

  val cc1 = ContaCorrente(titular = "Ana Silva", numero = 1001, taxaSaque = 3.0)

  val cp1 = ContaPoupanca(titular = "Carlos Souza", numero = 2001, taxaRendimento = 0.008)

  // 2. Exibição inicial dos dados

  cc1.exibeDados()

  cp1.exibeDados()

  println("\n=== REALIZANDO MOVIMENTAÇÕES FINANCEIRAS ===")

  // Testando Depósitos (incluindo tentativa de depósito inválido)

  cc1.deposita(1500.00)

  cc1.deposita(-200.00) // Deve rejeitar

  cp1.deposita(3000.00)

  // Testando Saques e Regras de Negócio (Taxas e Saldo Negativo)

  cc1.sacar(200.00) // Deve descontar o valor + taxa de R$ 3,00

  // Tentativa de saque maior que o saldo

  cc1.sacar(5000.00) // Deve impedir saldo negativo

  // Aplicando Rendimento na Poupança

  cp1.aplicaRendimento()

  println("\n=== ESTADO FINAL DAS CONTAS ===")

  cc1.exibeDados()

  cp1.exibeDados()

  // Exibindo Histórico de Operações

  cc1.exibeHistorico()

  cp1.exibeHistorico()

}