package task_2;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import task_1.Employee;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static task_1.Main.writeString;
import static task_1.Main.listToJson;

public class Main {
    public static void main(String[] args)
            throws ParserConfigurationException, IOException, SAXException {
        List<Employee> list = parseXML("data.xml");
        String json = listToJson(list);
        writeString(json, "data2.json");

    }

    private static List<Employee> parseXML(String fileName)
            throws ParserConfigurationException, IOException, SAXException {
        List<Employee> answer = new ArrayList<>();

        // получаем экземпляр класса Document
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File(fileName));

        // получаем корневой узел документа
        Node root = doc.getDocumentElement();

        // получаем список дочерних узлов
        NodeList nodeList = root.getChildNodes();

        // проходимся по списку узлов и получаем из каждого из них Element
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (Node.ELEMENT_NODE == node.getNodeType()) {
                Element element = (Element) node;
                Employee newEmployee = new Employee();
                newEmployee.id =
                        Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                newEmployee.firstName =
                        element.getElementsByTagName("firstName").item(0).getTextContent();
                newEmployee.lastName =
                        element.getElementsByTagName("lastName").item(0).getTextContent();
                newEmployee.country =
                        element.getElementsByTagName("country").item(0).getTextContent();
                newEmployee.age =
                        Integer.parseInt(element.getElementsByTagName("age").item(0).getTextContent());
                answer.add(newEmployee);
            }
        }
        return answer;
    }
}
