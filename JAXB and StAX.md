```java
package com.mytutorial;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class App {
    public static void main(String[] args) throws JAXBException, IOException, XMLStreamException, URISyntaxException {
        // Java 1.6 onwards
        JAXBContext jaxbContext = JAXBContext.newInstance(Books.class);

        // unmarshall: String "source" to Java object
        Unmarshaller um = jaxbContext.createUnmarshaller();

        XMLInputFactory xmlFactory = XMLInputFactory.newInstance();
        XMLStreamReader reader = xmlFactory.createXMLStreamReader(new FileReader(getFile()));

        // interested in "book" elements only. Skip up to first "book"
        while (reader.hasNext() && (!reader.isStartElement() || !reader.getLocalName().equals("book"))) {
            reader.next();
        }

        // read a book at a time
        while (reader.getEventType() == XMLStreamConstants.START_ELEMENT) {
            JAXBElement<Book> boolElement = um.unmarshal(reader, Book.class);
            Book book = boolElement.getValue();
            if (book.getAuthor() != null) {  //skip footer tag
                System.out.println(book.getAuthor() + " wrote " + book.getTitle());
            }

            if (reader.getEventType() == XMLStreamConstants.CHARACTERS) {
                reader.next();
            }
        }

        reader.close();
    }

    /**
     * 
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    private static final File getFile() throws IOException, URISyntaxException {
        Path path = Paths.get(ClassLoader.getSystemResource("xml/books.xml").toURI());
        return path.toFile();
    }
}
```
