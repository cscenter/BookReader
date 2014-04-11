package reader;
import model.Model;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XMLReader {
    private String nameOfFile;
    private Model model;
    
    public XMLReader(String name) throws ReaderException {
        nameOfFile = name;
    }
    
    public void readXML() throws ReaderException{
        try {
            File file = new File(nameOfFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
            
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            model = (Model) jaxbUnmarshaller.unmarshal(file);
 
	  } catch (JAXBException e) {
              e.printStackTrace();
              throw new ReaderException("Can't read XML:\n" + e.getMessage());
	  }
    }
    
    public void writeXML() throws ReaderException{
        try {
            File file = new File(nameOfFile);
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
    
    public Model getModel(){
        return model;
    }
    
    public void setModel(Model model){
        this.model = model;
    }
    
    public String getNameOfFile(){
        return nameOfFile;
    }
    
    public void setNameOfFile(String nameOfFile){
        this.nameOfFile = nameOfFile;
    }
}
