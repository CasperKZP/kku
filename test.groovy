import groovy.json.JsonSlurper

println 'helloworld'

def agents = [:]
def jsonSlurper = new JsonSlurper()
data = jsonSlurper.parse(new File('app.properties'))
//println(data)

//def props = readJSON file: 'app.properties', returnPojo: true
//assert props['key'] == null
//props.each { key, value ->
//    echo "Walked through key $key and value $value"

for (item in data.bases) {
    if (!item.used) { continue }
    print item.hostName
    print item.used
    println item.version
    agents["agent_${item.hostName}"] = echoAgent(item.hostName)
    }

//}
//println(data['bases'].name[0])

def echoAgent(hostName)
{
    echo "success"
}