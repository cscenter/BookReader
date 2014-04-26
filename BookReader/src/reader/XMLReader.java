package reader;
import model.Model;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLReader {
    
    public XMLReader() throws ReaderException {
    }
    
    public Model readXML(String fileName) throws ReaderException{
        try {
            File file = new File(fileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
            
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Model model = (Model) jaxbUnmarshaller.unmarshal(file);
            return model;
            
	  } catch (JAXBException e) {
              e.printStackTrace();
              throw new ReaderException("Can't read XML:\n" + e.getMessage());
	  }
    }
    
    public void writeXML(String fileName, Model model) throws ReaderException{
        try {
            File file = new File(fileName);
            System.out.println(file.getAbsolutePath());
            JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
 
            jaxbMarshaller.marshal(model, file);
	    } catch (JAXBException e) {
                e.printStackTrace();
                throw new ReaderException("Can't write XML:\n" + e.getMessage());
            }
    }
}
