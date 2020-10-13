def a0 = 'ELECTROENCEPHALOGRAPHS'

def aaa = a0.split('')
println aaa

def bbb = aaa.countBy { it }
println bbb

def ccc = bbb.values().max()

println "ccc=$ccc"

def ddd = a0.bytes
def bitmask = 0
def A_byte = 'A'.bytes[0]
for (int i=0;i<a0.length();i++) {
  def letter_code = ddd[i]-A_byte
  bitmask |= (1<<letter_code)
}

println "0b${Integer.toBinaryString(bitmask)}"


println Integer.toBinaryString( (1<<8)-1 )
