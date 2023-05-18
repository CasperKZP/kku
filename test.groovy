import groovy.json.JsonSlurper
println 'helloworld'

def jsonSlurper = new JsonSlurper()
data = jsonSlurper.parse(new File('app.properties'))
//println(data)

//def props = readJSON file: 'app.properties', returnPojo: true
//assert props['key'] == null
//props.each { key, value ->
//    echo "Walked through key $key and value $value"

for (item in data.bases)
{
    if (!item.used) {continue}
    print item.name
    print item.used
    println item.version}


//}
//println(data['bases'].name[0])