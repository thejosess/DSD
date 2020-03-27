$:.push('gen-rb')
$:.unshift '../../lib/rb/lib'

require_relative "thrift"

require 'calculadora'
require 'calculadora_types'

class CalculadoraHandler
  def initialize()
    @log = {}
  end

  def ping()
    puts "ping()"
  end

  def suma(num1, num2)
    return num1 + num2
  end

  def resta(num1, num2)
    return num1 - num2
  end
  
  def division(num1, num2)
    return num1 / num2
  end

  def multiplicacion(num1, num2)
    return num1 * num2
  end

  def modulo(num1, num2)
    return num1 % num2
  end

  def sumarVectores(v1, v2)
    valor = v1.length - 1
    v3 = Array.new(valor)
    for i in 0..valor
      v3[i] = v1[i] + v2[i]
    end
    return v3
  end

  def restarVectores(v1, v2)
    valor = v1.length - 1
    v3 = Array.new(valor)
    for i in 0..valor
      v3[i] = v1[i] - v2[i]
    end
    return v3
  end

  def multiplicarVectores(v1, v2)
    valor = v1.length - 1
    v3 = Array.new(valor)
    for i in 0..valor
      v3[i] = v1[i] * v2[i]
    end
    return v3
  end

  def dividirVectores(v1, v2)
    valor = v1.length - 1
    v3 = Array.new(valor)
    for i in 0..valor
      v3[i] = v1[i] / v2[i]
    end
    return v3
  end

end

handler = CalculadoraHandler.new()
processor = Calculadora::Processor.new(handler)
transport = Thrift::ServerSocket.new('127.0.0.1',9090)
transportFactory = Thrift::BufferedTransportFactory.new()
server = Thrift::SimpleServer.new(processor, transport, transportFactory)

puts "Starting the server..."
server.serve()
puts "done."
