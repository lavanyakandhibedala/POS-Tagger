import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilder.*;
import javax.xml.parsers.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
/*
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;  */
import org.w3c.dom.*;
import java.io.FileReader;
import javax.xml.xpath.XPath;
import javax.xml.xpath.*;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;




//*************************************************        http://nlpdotnet.com/services/Tagger.aspx              **********************************



public class POS_Tagging
{
  public static class word_tag
  {
    /*
    noun-nun,
    pronoun-pnu,
    adverb-adv,
    verb-vrb,
    interjection-intj,
    preposition-ppt,
    adjective-adj,
    conjunction-con,
    article-art
    others-oth
    */
    public float nun,pnu,adv,adj,con,intj,art,ppt,vrb,oth;
    word_tag()
    {
      nun=pnu=adv=adj=con=intj=art=ppt=vrb=oth=0;
    }
  }public static word_tag noun_tp, pronoun_tp, adverb_tp, verb_tp, interjection_tp, preposition_tp, adjective_tp, conjunction_tp, article_tp,other_tp;
  
  
  public static class pair
  {
    public String key=new String();
    public String value=new String();
    pair()
    {
      key=value=null;
    }
    pair(String line)
    {
      int i=0;
      while(line.charAt(i)!='/')
      {
        key+=line.charAt(i++);
        
      }
      i++;
      while(i<line.length())
      {
        value+=line.charAt(i++);
      }
    }
    
  }public static pair[] data_block=new pair[100];
  
  
  
  public static word_tag retrive_word_data_from_xml(String words)
   {
     word_tag result=new word_tag();
     try
     {
       DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
       DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
       Document doc = docBuilder.parse("Training_Data_tp_data.xml");
       if((int)doc.getElementsByTagName(words).getLength()==0||doc.getElementsByTagName(words).item(0).getNodeName().toString()==null){
    	   return result;}
       NodeList nl1=(NodeList)doc.getElementsByTagName(words);
       
       if(nl1.item(0).getNodeName()==null)
       {
    	   

    	   return result;
       }
       Element el=(Element)nl1.item(0);
       NodeList nl=el.getChildNodes();
       int i=0;
       
         Node node = nl.item(i++);
         result.nun=Float.parseFloat(node.getTextContent());
         node = nl.item(i++);
         result.pnu=Float.parseFloat(node.getTextContent());
         
         node = nl.item(i++);
         result.adj=Float.parseFloat(node.getTextContent());
         
         node = nl.item(i++);
         result.adv=Float.parseFloat(node.getTextContent());
         
         node = nl.item(i++);
         result.art=Float.parseFloat(node.getTextContent());
         
         node = nl.item(i++);
         result.con=Float.parseFloat(node.getTextContent());
         
         node = nl.item(i++);
         result.intj=Float.parseFloat(node.getTextContent());
         
         node = nl.item(i++);
         result.ppt=Float.parseFloat(node.getTextContent());
         
         node = nl.item(i++);
         result.vrb=Float.parseFloat(node.getTextContent());
         
         node = nl.item(i);
         result.oth=Float.parseFloat(node.getTextContent());
  
     }
     catch(Exception ee)
     {
       System.out.println("exceprion is retrive_word_data_from_xml:   "+ee);
     }
     
     
     
     return result;
     
   }
    
  
  public static void Transitional_probability(String POS1,String POS2)
   {
    if(POS1=="NN"||POS1=="NNS"||POS1=="NNP"||POS1=="NNPS")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        noun_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        noun_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        noun_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        noun_tp.adv+=1;
      if(POS2=="DT")
        noun_tp.art+=1;
      if(POS2=="CC")
        noun_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        noun_tp.ppt+=1;
      if(POS2=="UH")
        noun_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        noun_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        noun_tp.oth+=1;
    }
    if(POS1=="PRP"||POS1=="PRP$"||POS1=="WP"||POS1=="WP$")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        pronoun_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        pronoun_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        pronoun_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        pronoun_tp.adv+=1;
      if(POS2=="DT")
        pronoun_tp.art+=1;
      if(POS2=="CC")
        pronoun_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        pronoun_tp.ppt+=1;
      if(POS2=="UH")
        pronoun_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        pronoun_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        pronoun_tp.oth+=1;
    }
    if(POS1=="JJ"||POS1=="JJR"||POS1=="JJS")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        adjective_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        adjective_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        adjective_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        adjective_tp.adv+=1;
      if(POS2=="DT")
        adjective_tp.art+=1;
      if(POS2=="CC")
        adjective_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        adjective_tp.ppt+=1;
      if(POS2=="UH")
        adjective_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        adjective_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        adjective_tp.oth+=1;
    }
    if(POS1=="RB"||POS1=="RBR"||POS1=="RBS"||POS1=="WRB")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        adverb_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        adverb_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        adverb_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        adverb_tp.adv+=1;
      if(POS2=="DT")
        adverb_tp.art+=1;
      if(POS2=="CC")
        adverb_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        adverb_tp.ppt+=1;
      if(POS2=="UH")
        adverb_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        adverb_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        adverb_tp.oth+=1;
    }
    if(POS1=="DT")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        article_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        article_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        article_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        article_tp.adv+=1;
      if(POS2=="DT")
        article_tp.art+=1;
      if(POS2=="CC")
        article_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        article_tp.ppt+=1;
      if(POS2=="UH")
        article_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        article_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        article_tp.oth+=1;
    }
    if(POS1=="CC")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        conjunction_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        conjunction_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        conjunction_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        conjunction_tp.adv+=1;
      if(POS2=="DT")
        conjunction_tp.art+=1;
      if(POS2=="CC")
        conjunction_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        conjunction_tp.ppt+=1;
      if(POS2=="UH")
        conjunction_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        conjunction_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        conjunction_tp.oth+=1;
    }
    if(POS1=="IN"||POS1=="TO"||POS1=="RP")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        preposition_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        preposition_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        preposition_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        preposition_tp.adv+=1;
      if(POS2=="DT")
        preposition_tp.art+=1;
      if(POS2=="CC")
        preposition_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        preposition_tp.ppt+=1;
      if(POS2=="UH")
        preposition_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        preposition_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        preposition_tp.oth+=1;
    }
    if(POS1=="UH")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        interjection_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        interjection_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        interjection_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        interjection_tp.adv+=1;
      if(POS2=="DT")
        interjection_tp.art+=1;
      if(POS2=="CC")
        interjection_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        interjection_tp.ppt+=1;
      if(POS2=="UH")
        interjection_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        interjection_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        interjection_tp.oth+=1;
    }
    if(POS1=="VB"||POS1=="VBD"||POS1=="VBG"||POS1=="VBN"||POS1=="VBP"||POS1=="VBZ"||POS1=="MD")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        verb_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        verb_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        verb_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        verb_tp.adv+=1;
      if(POS2=="DT")
        verb_tp.art+=1;
      if(POS2=="CC")
        verb_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        verb_tp.ppt+=1;
      if(POS2=="UH")
        verb_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        verb_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        verb_tp.oth+=1;
    }
    if(POS1=="EX"||POS1=="CD"||POS1=="FW"||POS1=="LS"||POS1=="PDT"||POS1=="POS"||POS1=="SYM"||POS1=="WDT")
    {
      if(POS2=="NN"||POS2=="NNS"||POS2=="NNP"||POS2=="NNPS")
        other_tp.nun+=1;
      if(POS2=="PRP"||POS2=="PRP$"||POS2=="WP"||POS2=="WP$")
        other_tp.pnu+=1;
      if(POS2=="JJ"||POS2=="JJR"||POS2=="JJS")
        other_tp.adj+=1;
      if(POS2=="RB"||POS2=="RBR"||POS2=="RBS"||POS2=="WRB")
        other_tp.adv+=1;
      if(POS2=="DT")
        other_tp.art+=1;
      if(POS2=="CC")
        other_tp.con+=1;
      if(POS2=="IN"||POS2=="TO"||POS2=="RP")
        other_tp.ppt+=1;
      if(POS2=="UH")
        other_tp.intj+=1;
      if(POS2=="VB"||POS2=="VBD"||POS2=="VBG"||POS2=="VBN"||POS2=="VBP"||POS2=="VBZ"||POS2=="MD")
        other_tp.vrb+=1;
      if(POS2=="EX"||POS2=="CD"||POS2=="FW"||POS2=="LS"||POS2=="PDT"||POS2=="POS"||POS2=="SYM"||POS2=="WDT")
        other_tp.oth+=1;
      
    }
    
   }
   

  
  public static void write_word_in_xml(pair word)
  {
     
    word_tag wrd=new word_tag();
    wrd=retrive_word_data_from_xml(word.key);
    
     if(word.value.equals("NN")||word.value.equals("NNS")||word.value.equals("NNP")||word.value.equals("NNPS")) 
        wrd.nun+=1;
      if(word.value.equals("PRP")||word.value.equals("PRP$")||word.value.equals("WP")||word.value.equals("WP$"))
        wrd.pnu+=1;
      if(word.value.equals("JJ")||word.value.equals("JJR")||word.value.equals("JJS"))
        wrd.adj+=1;
      if(word.value.equals("RB")||word.value.equals("RBR")||word.value.equals("RBS")||word.value.equals("WRB"))
        wrd.adv+=1;
      if(word.value.equals("DT"))
        wrd.art+=1;
      if(word.value.equals("CC"))
        wrd.con+=1;
      if(word.value.equals("IN")||word.value.equals("TO")||word.value.equals("RP"))
        wrd.ppt+=1;
      if(word.value.equals("UH"))
        wrd.intj+=1;
      if(word.value.equals("VB")||word.value.equals("VBD")||word.value.equals("VBG")||word.value.equals("VBN")||word.value.equals("VBP")||word.value.equals("VBZ")||word.value.equals("MD"))
        wrd.vrb+=1;
      if(word.value.equals("EX")||word.value.equals("CD")||word.value.equals("FW")||word.value.equals("LS")||word.value.equals("PDT")||word.value.equals("POS")||word.value.equals("SYM")||word.value.equals("WDT"))
        wrd.oth+=1;
    
    float total=wrd.nun+wrd.pnu+wrd.adv+wrd.adj+wrd.con+wrd.intj+wrd.art+wrd.ppt+wrd.vrb+wrd.oth;
    
     try
     {
       
   
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    Document Prob_File=docBuilder.parse("Training_Data_tp_data.xml");
    
    Element words=Prob_File.getDocumentElement();
    int fg=0;
    try
    {
    	Element ppl =  (Element) words.getElementsByTagName(word.key).item(0);
    	if(ppl==null)
    		throw new Exception("error");
    }
    catch(Exception ee)
    {
    	fg=1;
    }
    if(total==1&&fg==1)
    {
       
       Element wd=Prob_File.createElement(word.key);       
       
      Element noun=Prob_File.createElement("Noun");
      wd.appendChild(noun);
      noun.appendChild(Prob_File.createTextNode(Float.toString(wrd.nun)));
      
      Element pronoun=Prob_File.createElement("Pronoun");
      wd.appendChild(pronoun);
      pronoun.appendChild(Prob_File.createTextNode(Float.toString(wrd.pnu)));
      
      Element adjective=Prob_File.createElement("Adjective");
      wd.appendChild(adjective);
      adjective.appendChild(Prob_File.createTextNode(Float.toString(wrd.adj)));
      
      Element adverb=Prob_File.createElement("Adverb");
      wd.appendChild(adverb);
      adverb.appendChild(Prob_File.createTextNode(Float.toString(wrd.adv)));
      
      Element article=Prob_File.createElement("Article");
      wd.appendChild(article);
      article.appendChild(Prob_File.createTextNode(Float.toString(wrd.art)));
      
      Element conjunction=Prob_File.createElement("Conjunction");
      wd.appendChild(conjunction);
      conjunction.appendChild(Prob_File.createTextNode(Float.toString(wrd.con)));
      
      Element interjection=Prob_File.createElement("Interjection");
      wd.appendChild(interjection);
      interjection.appendChild(Prob_File.createTextNode(Float.toString(wrd.intj)));
      
      Element preposition=Prob_File.createElement("Preposition");
      wd.appendChild(preposition);
      preposition.appendChild(Prob_File.createTextNode(Float.toString(wrd.ppt)));
      
      Element verb=Prob_File.createElement("Verb");
      wd.appendChild(verb);
      verb.appendChild(Prob_File.createTextNode(Float.toString(wrd.vrb)));
      
      Element other=Prob_File.createElement("Others");
      wd.appendChild(other);
      other.appendChild(Prob_File.createTextNode(Float.toString(wrd.oth)));
      
      words.appendChild(wd);
    }
    else
    {   
    	 
        Node cur_wrd = Prob_File.getElementsByTagName(word.key).item(0);
        // loop the staff child node
        
        NodeList list = cur_wrd.getChildNodes();
        
        int i = 0;
        
        Node node = list.item(i++);
        node.setTextContent(Float.toString(wrd.nun));
        
      
        node = list.item(i++);
        node.setTextContent(Float.toString(wrd.pnu));
        
      
        node = list.item(i++);
        node.setTextContent(Float.toString(wrd.adj));
      
        node = list.item(i++);
        node.setTextContent(Float.toString(wrd.adv));
      
        node = list.item(i++);
        node.setTextContent(Float.toString(wrd.art));
      
        node = list.item(i++);
        node.setTextContent(Float.toString(wrd.con));
      
        node = list.item(i++);
        node.setTextContent(Float.toString(wrd.intj));
      
        node = list.item(i++);
        node.setTextContent(Float.toString(wrd.ppt));
      
        node = list.item(i++);
        node.setTextContent(Float.toString(wrd.vrb));
      
        node = list.item(i);
        node.setTextContent(Float.toString(wrd.oth));
 
    }
    
    
    
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(Prob_File);
      StreamResult result = new StreamResult(new File("Training_Data_tp_data.xml"));
      transformer.transform(source, result);
      }
      catch(Exception ee)
      {
         //System.out.println("Exception of write_word_in_xml:  "+ee);
    	  return;
      }
  }
  
  
  
  public static void calculate_count_n_tp(int n)
  {
    int i;
    for(i=0;i<n;i++)
    {
      
      write_word_in_xml(data_block[i]);
      if(i<n-1)
      Transitional_probability(data_block[i].value,data_block[i+1].value);
    }
  }
 

  
  public static void add_transitional_probability_to_xml_file()
   {
    
    try
    {
      
               
    String total=new String();
    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    
    Document Prob_File=docBuilder.newDocument();
    Element transtion_prob=Prob_File.createElement("Transition-Probabilities");
    Prob_File.appendChild(transtion_prob);
    
    
    Element noun_tps=Prob_File.createElement("Noun");
    transtion_prob.appendChild(noun_tps);
    Element noun=Prob_File.createElement("Noun");
    noun_tps.appendChild(noun);
    total=Float.toString(noun_tp.nun/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    noun.appendChild(Prob_File.createTextNode(total));  
    
    Element pronoun=Prob_File.createElement("Pronoun");
    noun_tps.appendChild(pronoun);
    total=Float.toString(noun_tp.nun/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    pronoun.appendChild(Prob_File.createTextNode(total));
    
    Element adjective=Prob_File.createElement("Adjective");
    noun_tps.appendChild(adjective);
    total=Float.toString(noun_tp.nun/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    adjective.appendChild(Prob_File.createTextNode(total));
    
    Element adverb=Prob_File.createElement("Adverb");
    noun_tps.appendChild(adverb);
    total=Float.toString(noun_tp.nun/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    adverb.appendChild(Prob_File.createTextNode(total));
    
    Element article=Prob_File.createElement("Article");
    noun_tps.appendChild(article);
    total=Float.toString(noun_tp.nun/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    article.appendChild(Prob_File.createTextNode(total));
    
    Element conjunction=Prob_File.createElement("Conjunction");
    noun_tps.appendChild(conjunction);
    total=Float.toString(noun_tp.nun/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    conjunction.appendChild(Prob_File.createTextNode(total));
    
    Element interjection=Prob_File.createElement("Interjection");
    noun_tps.appendChild(interjection);
    total=Float.toString(noun_tp.nun/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    interjection.appendChild(Prob_File.createTextNode(total));
    
    Element preposition=Prob_File.createElement("Preposition");
    noun_tps.appendChild(preposition);
    total=Float.toString(noun_tp.ppt/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    preposition.appendChild(Prob_File.createTextNode(total));
    
    Element verb=Prob_File.createElement("Verb");
    noun_tps.appendChild(verb);
    total=Float.toString(noun_tp.vrb/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    verb.appendChild(Prob_File.createTextNode(total));
    
    Element other=Prob_File.createElement("Others");
    noun_tps.appendChild(other);
    total=Float.toString(noun_tp.oth/(noun_tp.nun+noun_tp.pnu+noun_tp.ppt+noun_tp.art+noun_tp.vrb+noun_tp.adj+noun_tp.adv+noun_tp.intj+noun_tp.con+noun_tp.oth));
    other.appendChild(Prob_File.createTextNode(total));
    
    
    
    
      

  Element pronoun_tps=Prob_File.createElement("Pronoun");
  transtion_prob.appendChild(pronoun_tps);
  noun=Prob_File.createElement("Noun");
  pronoun_tps.appendChild(noun);
  total=Float.toString(pronoun_tp.nun/(pronoun_tp.nun+pronoun_tp.pnu+pronoun_tp.ppt+pronoun_tp.art+pronoun_tp.vrb+pronoun_tp.adj+pronoun_tp.adv+pronoun_tp.intj+pronoun_tp.con+pronoun_tp.oth));
  noun.appendChild(Prob_File.createTextNode(total));  
  
  pronoun=Prob_File.createElement("Pronoun");
  pronoun_tps.appendChild(pronoun);
  total=Float.toString(pronoun_tp.nun/(pronoun_tp.nun+pronoun_tp.pnu+pronoun_tp.ppt+pronoun_tp.art+pronoun_tp.vrb+pronoun_tp.adj+pronoun_tp.adv+pronoun_tp.intj+pronoun_tp.con+pronoun_tp.oth));
  pronoun.appendChild(Prob_File.createTextNode(total));
  
  adjective=Prob_File.createElement("Adjective");
  pronoun_tps.appendChild(adjective);
  total=Float.toString(pronoun_tp.nun/(pronoun_tp.nun+pronoun_tp.pnu+pronoun_tp.ppt+pronoun_tp.art+pronoun_tp.vrb+pronoun_tp.adj+pronoun_tp.adv+pronoun_tp.intj+pronoun_tp.con+pronoun_tp.oth));
  adjective.appendChild(Prob_File.createTextNode(total));
  
  adverb=Prob_File.createElement("Adverb");
  pronoun_tps.appendChild(adverb);
  total=Float.toString(pronoun_tp.nun/(pronoun_tp.nun+pronoun_tp.pnu+pronoun_tp.ppt+pronoun_tp.art+pronoun_tp.vrb+pronoun_tp.adj+pronoun_tp.adv+pronoun_tp.intj+pronoun_tp.con+pronoun_tp.oth));
  adverb.appendChild(Prob_File.createTextNode(total));
  
  article=Prob_File.createElement("Article");
  pronoun_tps.appendChild(article);
  total=Float.toString(pronoun_tp.nun/(pronoun_tp.nun+pronoun_tp.pnu+pronoun_tp.ppt+pronoun_tp.art+pronoun_tp.vrb+pronoun_tp.adj+pronoun_tp.adv+pronoun_tp.intj+pronoun_tp.con+pronoun_tp.oth));
  article.appendChild(Prob_File.createTextNode(total));
  
  conjunction=Prob_File.createElement("Conjunction");
  pronoun_tps.appendChild(conjunction);
  total=Float.toString(pronoun_tp.nun/(pronoun_tp.nun+pronoun_tp.pnu+pronoun_tp.ppt+pronoun_tp.art+pronoun_tp.vrb+pronoun_tp.adj+pronoun_tp.adv+pronoun_tp.intj+pronoun_tp.con+pronoun_tp.oth));
  conjunction.appendChild(Prob_File.createTextNode(total));
  
  interjection=Prob_File.createElement("Interjection");
  pronoun_tps.appendChild(interjection);
  total=Float.toString(pronoun_tp.nun/(pronoun_tp.nun+pronoun_tp.pnu+pronoun_tp.ppt+pronoun_tp.art+pronoun_tp.vrb+pronoun_tp.adj+pronoun_tp.adv+pronoun_tp.intj+pronoun_tp.con+pronoun_tp.oth));
  interjection.appendChild(Prob_File.createTextNode(total));
  
  preposition=Prob_File.createElement("Preposition");
  pronoun_tps.appendChild(preposition);
  total=Float.toString(pronoun_tp.ppt/(pronoun_tp.nun+pronoun_tp.pnu+pronoun_tp.ppt+pronoun_tp.art+pronoun_tp.vrb+pronoun_tp.adj+pronoun_tp.adv+pronoun_tp.intj+pronoun_tp.con+pronoun_tp.oth));
  preposition.appendChild(Prob_File.createTextNode(total));
  
  verb=Prob_File.createElement("Verb");
  pronoun_tps.appendChild(verb);
  total=Float.toString(pronoun_tp.vrb/(pronoun_tp.nun+pronoun_tp.pnu+pronoun_tp.ppt+pronoun_tp.art+pronoun_tp.vrb+pronoun_tp.adj+pronoun_tp.adv+pronoun_tp.intj+pronoun_tp.con+pronoun_tp.oth));
  verb.appendChild(Prob_File.createTextNode(total));
  
  other=Prob_File.createElement("Others");
  pronoun_tps.appendChild(other);
  
  
  
  
  
  
  Element adjective_tps=Prob_File.createElement("Adjective");
  transtion_prob.appendChild(adjective_tps);
  noun=Prob_File.createElement("Noun");
  adjective_tps.appendChild(noun);
  total=Float.toString(adjective_tp.nun/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  noun.appendChild(Prob_File.createTextNode(total));  
  
  pronoun=Prob_File.createElement("Pronoun");
  adjective_tps.appendChild(pronoun);
  total=Float.toString(adjective_tp.nun/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  pronoun.appendChild(Prob_File.createTextNode(total));
  
  adjective=Prob_File.createElement("Adjective");
  adjective_tps.appendChild(adjective);
  total=Float.toString(adjective_tp.nun/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  adjective.appendChild(Prob_File.createTextNode(total));
  
  adverb=Prob_File.createElement("Adverb");
  adjective_tps.appendChild(adverb);
  total=Float.toString(adjective_tp.nun/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  adverb.appendChild(Prob_File.createTextNode(total));
  
  article=Prob_File.createElement("Article");
  adjective_tps.appendChild(article);
  total=Float.toString(adjective_tp.nun/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  article.appendChild(Prob_File.createTextNode(total));
  
  conjunction=Prob_File.createElement("Conjunction");
  adjective_tps.appendChild(conjunction);
  total=Float.toString(adjective_tp.nun/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  conjunction.appendChild(Prob_File.createTextNode(total));
  
  interjection=Prob_File.createElement("Interjection");
  adjective_tps.appendChild(interjection);
  total=Float.toString(adjective_tp.nun/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  interjection.appendChild(Prob_File.createTextNode(total));
  
  preposition=Prob_File.createElement("Preposition");
  adjective_tps.appendChild(preposition);
  total=Float.toString(adjective_tp.ppt/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  preposition.appendChild(Prob_File.createTextNode(total));
  
  verb=Prob_File.createElement("Verb");
  adjective_tps.appendChild(verb);
  total=Float.toString(adjective_tp.vrb/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  verb.appendChild(Prob_File.createTextNode(total));
  
  other=Prob_File.createElement("Others");
  adjective_tps.appendChild(other);
  total=Float.toString(adjective_tp.oth/(adjective_tp.nun+adjective_tp.pnu+adjective_tp.ppt+adjective_tp.art+adjective_tp.vrb+adjective_tp.adj+adjective_tp.adv+adjective_tp.intj+adjective_tp.con+adjective_tp.oth));
  other.appendChild(Prob_File.createTextNode(total));
  
  
  
  
  
  
  Element adverb_tps=Prob_File.createElement("Adverb");
  transtion_prob.appendChild(adverb_tps);
  noun=Prob_File.createElement("Noun");
  adverb_tps.appendChild(noun);
  total=Float.toString(adverb_tp.nun/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  noun.appendChild(Prob_File.createTextNode(total));  
  
  pronoun=Prob_File.createElement("Pronoun");
  adverb_tps.appendChild(pronoun);
  total=Float.toString(adverb_tp.nun/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  pronoun.appendChild(Prob_File.createTextNode(total));
  
  adjective=Prob_File.createElement("Adjective");
  adverb_tps.appendChild(adjective);
  total=Float.toString(adverb_tp.nun/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  adjective.appendChild(Prob_File.createTextNode(total));
  
  adverb=Prob_File.createElement("Adverb");
  adverb_tps.appendChild(adverb);
  total=Float.toString(adverb_tp.nun/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  adverb.appendChild(Prob_File.createTextNode(total));
  
  article=Prob_File.createElement("Article");
  adverb_tps.appendChild(article);
  total=Float.toString(adverb_tp.nun/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  article.appendChild(Prob_File.createTextNode(total));
  
  conjunction=Prob_File.createElement("Conjunction");
  adverb_tps.appendChild(conjunction);
  total=Float.toString(adverb_tp.nun/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  conjunction.appendChild(Prob_File.createTextNode(total));
  
  interjection=Prob_File.createElement("Interjection");
  adverb_tps.appendChild(interjection);
  total=Float.toString(adverb_tp.nun/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  interjection.appendChild(Prob_File.createTextNode(total));
  
  preposition=Prob_File.createElement("Preposition");
  adverb_tps.appendChild(preposition);
  total=Float.toString(adverb_tp.ppt/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  preposition.appendChild(Prob_File.createTextNode(total));
  
  verb=Prob_File.createElement("Verb");
  adverb_tps.appendChild(verb);
  total=Float.toString(adverb_tp.vrb/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  verb.appendChild(Prob_File.createTextNode(total));
  
  other=Prob_File.createElement("Others");
  adverb_tps.appendChild(other);
  total=Float.toString(adverb_tp.oth/(adverb_tp.nun+adverb_tp.pnu+adverb_tp.ppt+adverb_tp.art+adverb_tp.vrb+adverb_tp.adj+adverb_tp.adv+adverb_tp.intj+adverb_tp.con+adverb_tp.oth));
  other.appendChild(Prob_File.createTextNode(total));
  
  
  
  
  
  Element article_tps=Prob_File.createElement("Article");
  transtion_prob.appendChild(article_tps);
  noun=Prob_File.createElement("Noun");
  article_tps.appendChild(noun);
  total=Float.toString(article_tp.nun/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  noun.appendChild(Prob_File.createTextNode(total));  
  
  pronoun=Prob_File.createElement("Pronoun");
  article_tps.appendChild(pronoun);
  total=Float.toString(article_tp.nun/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  pronoun.appendChild(Prob_File.createTextNode(total));
  
  adjective=Prob_File.createElement("Adjective");
  article_tps.appendChild(adjective);
  total=Float.toString(article_tp.nun/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  adjective.appendChild(Prob_File.createTextNode(total));
  
  adverb=Prob_File.createElement("Adverb");
  article_tps.appendChild(adverb);
  total=Float.toString(article_tp.nun/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  adverb.appendChild(Prob_File.createTextNode(total));
  
  article=Prob_File.createElement("Article");
  article_tps.appendChild(article);
  total=Float.toString(article_tp.nun/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  article.appendChild(Prob_File.createTextNode(total));
  
  conjunction=Prob_File.createElement("Conjunction");
  article_tps.appendChild(conjunction);
  total=Float.toString(article_tp.nun/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  conjunction.appendChild(Prob_File.createTextNode(total));
  
  interjection=Prob_File.createElement("Interjection");
  article_tps.appendChild(interjection);
  total=Float.toString(article_tp.nun/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  interjection.appendChild(Prob_File.createTextNode(total));
  
  preposition=Prob_File.createElement("Preposition");
  article_tps.appendChild(preposition);
  total=Float.toString(article_tp.ppt/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  preposition.appendChild(Prob_File.createTextNode(total));
  
  verb=Prob_File.createElement("Verb");
  article_tps.appendChild(verb);
  total=Float.toString(article_tp.vrb/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  verb.appendChild(Prob_File.createTextNode(total));
  
  other=Prob_File.createElement("Others");
  article_tps.appendChild(other);
  total=Float.toString(article_tp.oth/(article_tp.nun+article_tp.pnu+article_tp.ppt+article_tp.art+article_tp.vrb+article_tp.adj+article_tp.adv+article_tp.intj+article_tp.con+article_tp.oth));
  other.appendChild(Prob_File.createTextNode(total));
  
  
  
  
  
  
  
  Element conjunction_tps=Prob_File.createElement("Conjunction");
  transtion_prob.appendChild(conjunction_tps);
  noun=Prob_File.createElement("Noun");
  conjunction_tps.appendChild(noun);
  total=Float.toString(conjunction_tp.nun/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  noun.appendChild(Prob_File.createTextNode(total));  
  
  pronoun=Prob_File.createElement("Pronoun");
  conjunction_tps.appendChild(pronoun);
  total=Float.toString(conjunction_tp.nun/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  pronoun.appendChild(Prob_File.createTextNode(total));
  
  adjective=Prob_File.createElement("Adjective");
  conjunction_tps.appendChild(adjective);
  total=Float.toString(conjunction_tp.nun/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  adjective.appendChild(Prob_File.createTextNode(total));
  
  adverb=Prob_File.createElement("Adverb");
  conjunction_tps.appendChild(adverb);
  total=Float.toString(conjunction_tp.nun/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  adverb.appendChild(Prob_File.createTextNode(total));
  
  article=Prob_File.createElement("Article");
  conjunction_tps.appendChild(article);
  total=Float.toString(conjunction_tp.nun/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  article.appendChild(Prob_File.createTextNode(total));
  
  conjunction=Prob_File.createElement("Conjunction");
  conjunction_tps.appendChild(conjunction);
  total=Float.toString(conjunction_tp.nun/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  conjunction.appendChild(Prob_File.createTextNode(total));
  
  interjection=Prob_File.createElement("Interjection");
  conjunction_tps.appendChild(interjection);
  total=Float.toString(conjunction_tp.nun/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  interjection.appendChild(Prob_File.createTextNode(total));
  
  preposition=Prob_File.createElement("Preposition");
  conjunction_tps.appendChild(preposition);
  total=Float.toString(conjunction_tp.ppt/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  preposition.appendChild(Prob_File.createTextNode(total));
  
  verb=Prob_File.createElement("Verb");
  conjunction_tps.appendChild(verb);
  total=Float.toString(conjunction_tp.vrb/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  verb.appendChild(Prob_File.createTextNode(total));
  
  other=Prob_File.createElement("Others");
  conjunction_tps.appendChild(other);
  total=Float.toString(conjunction_tp.oth/(conjunction_tp.nun+conjunction_tp.pnu+conjunction_tp.ppt+conjunction_tp.art+conjunction_tp.vrb+conjunction_tp.adj+conjunction_tp.adv+conjunction_tp.intj+conjunction_tp.con+conjunction_tp.oth));
  other.appendChild(Prob_File.createTextNode(total));
  
  
  
  
  
  
  Element interjection_tps=Prob_File.createElement("Interjection");
  transtion_prob.appendChild(interjection_tps);
  noun=Prob_File.createElement("Noun");
  interjection_tps.appendChild(noun);
  total=Float.toString(interjection_tp.nun/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  noun.appendChild(Prob_File.createTextNode(total));  
  
  pronoun=Prob_File.createElement("Pronoun");
  interjection_tps.appendChild(pronoun);
  total=Float.toString(interjection_tp.nun/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  pronoun.appendChild(Prob_File.createTextNode(total));
  
  adjective=Prob_File.createElement("Adjective");
  interjection_tps.appendChild(adjective);
  total=Float.toString(interjection_tp.nun/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  adjective.appendChild(Prob_File.createTextNode(total));
  
  adverb=Prob_File.createElement("Adverb");
  interjection_tps.appendChild(adverb);
  total=Float.toString(interjection_tp.nun/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  adverb.appendChild(Prob_File.createTextNode(total));
  
  article=Prob_File.createElement("Article");
  interjection_tps.appendChild(article);
  total=Float.toString(interjection_tp.nun/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  article.appendChild(Prob_File.createTextNode(total));
  
  conjunction=Prob_File.createElement("Conjunction");
  interjection_tps.appendChild(conjunction);
  total=Float.toString(interjection_tp.nun/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  conjunction.appendChild(Prob_File.createTextNode(total));
  
  interjection=Prob_File.createElement("Interjection");
  interjection_tps.appendChild(interjection);
  total=Float.toString(interjection_tp.nun/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  interjection.appendChild(Prob_File.createTextNode(total));
  
  preposition=Prob_File.createElement("Preposition");
  interjection_tps.appendChild(preposition);
  total=Float.toString(interjection_tp.ppt/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  preposition.appendChild(Prob_File.createTextNode(total));
  
  verb=Prob_File.createElement("Verb");
  interjection_tps.appendChild(verb);
  total=Float.toString(interjection_tp.vrb/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  verb.appendChild(Prob_File.createTextNode(total));
  
  other=Prob_File.createElement("Others");
  interjection_tps.appendChild(other);
  total=Float.toString(interjection_tp.oth/(interjection_tp.nun+interjection_tp.pnu+interjection_tp.ppt+interjection_tp.art+interjection_tp.vrb+interjection_tp.adj+interjection_tp.adv+interjection_tp.intj+interjection_tp.con+interjection_tp.oth));
  other.appendChild(Prob_File.createTextNode(total));
  
  
  
  
  
  
  
  Element preposition_tps=Prob_File.createElement("Preposition");
  transtion_prob.appendChild(preposition_tps);
  noun=Prob_File.createElement("Noun");
  preposition_tps.appendChild(noun);
  total=Float.toString(preposition_tp.nun/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  noun.appendChild(Prob_File.createTextNode(total));  
  
  pronoun=Prob_File.createElement("Pronoun");
  preposition_tps.appendChild(pronoun);
  total=Float.toString(preposition_tp.nun/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  pronoun.appendChild(Prob_File.createTextNode(total));
  
  adjective=Prob_File.createElement("Adjective");
  preposition_tps.appendChild(adjective);
  total=Float.toString(preposition_tp.nun/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  adjective.appendChild(Prob_File.createTextNode(total));
  
  adverb=Prob_File.createElement("Adverb");
  preposition_tps.appendChild(adverb);
  total=Float.toString(preposition_tp.nun/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  adverb.appendChild(Prob_File.createTextNode(total));
  
  article=Prob_File.createElement("Article");
  preposition_tps.appendChild(article);
  total=Float.toString(preposition_tp.nun/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  article.appendChild(Prob_File.createTextNode(total));
  
  conjunction=Prob_File.createElement("Conjunction");
  preposition_tps.appendChild(conjunction);
  total=Float.toString(preposition_tp.nun/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  conjunction.appendChild(Prob_File.createTextNode(total));
  
  interjection=Prob_File.createElement("Interjection");
  preposition_tps.appendChild(interjection);
  total=Float.toString(preposition_tp.nun/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  interjection.appendChild(Prob_File.createTextNode(total));
  
  preposition=Prob_File.createElement("Preposition");
  preposition_tps.appendChild(preposition);
  total=Float.toString(preposition_tp.ppt/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  preposition.appendChild(Prob_File.createTextNode(total));
  
  verb=Prob_File.createElement("Verb");
  preposition_tps.appendChild(verb);
  total=Float.toString(preposition_tp.vrb/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  verb.appendChild(Prob_File.createTextNode(total));
  
  other=Prob_File.createElement("Others");
  preposition_tps.appendChild(other);
  total=Float.toString(preposition_tp.oth/(preposition_tp.nun+preposition_tp.pnu+preposition_tp.ppt+preposition_tp.art+preposition_tp.vrb+preposition_tp.adj+preposition_tp.adv+preposition_tp.intj+preposition_tp.con+preposition_tp.oth));
  other.appendChild(Prob_File.createTextNode(total));
  
  
  
  
  
  
  
  
  Element verb_tps=Prob_File.createElement("Verb");
  transtion_prob.appendChild(verb_tps);
  noun=Prob_File.createElement("Noun");
  verb_tps.appendChild(noun);
    total=Float.toString(verb_tp.nun/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
    noun.appendChild(Prob_File.createTextNode(total));  
  
  pronoun=Prob_File.createElement("Pronoun");
  verb_tps.appendChild(pronoun);
    total=Float.toString(verb_tp.nun/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
  pronoun.appendChild(Prob_File.createTextNode(total));
  
  adjective=Prob_File.createElement("Adjective");
  verb_tps.appendChild(adjective);
    total=Float.toString(verb_tp.nun/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
  adjective.appendChild(Prob_File.createTextNode(total));
  
  adverb=Prob_File.createElement("Adverb");
  verb_tps.appendChild(adverb);
    total=Float.toString(verb_tp.nun/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
    adverb.appendChild(Prob_File.createTextNode(total));
  
  article=Prob_File.createElement("Article");
  verb_tps.appendChild(article);
    total=Float.toString(verb_tp.nun/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
  article.appendChild(Prob_File.createTextNode(total));
  
  conjunction=Prob_File.createElement("Conjunction");
  verb_tps.appendChild(conjunction);
    total=Float.toString(verb_tp.nun/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
  conjunction.appendChild(Prob_File.createTextNode(total));
  
  interjection=Prob_File.createElement("Interjection");
  verb_tps.appendChild(interjection);
    total=Float.toString(verb_tp.nun/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
  interjection.appendChild(Prob_File.createTextNode(total));
  
  preposition=Prob_File.createElement("Preposition");
  verb_tps.appendChild(preposition);
    total=Float.toString(verb_tp.ppt/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
  preposition.appendChild(Prob_File.createTextNode(total));
  
  verb=Prob_File.createElement("Verb");
  verb_tps.appendChild(verb);
    total=Float.toString(verb_tp.vrb/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
    verb.appendChild(Prob_File.createTextNode(total));
  
  other=Prob_File.createElement("Others");
  verb_tps.appendChild(other);
    total=Float.toString(verb_tp.oth/(verb_tp.nun+verb_tp.pnu+verb_tp.ppt+verb_tp.art+verb_tp.vrb+verb_tp.adj+verb_tp.adv+verb_tp.intj+verb_tp.con+verb_tp.oth));
    other.appendChild(Prob_File.createTextNode(total));
  
  
  
  
  
  
  Element other_tps=Prob_File.createElement("Others");
  transtion_prob.appendChild(other_tps);
  noun=Prob_File.createElement("Noun");
  other_tps.appendChild(noun);
  total=Float.toString(other_tp.nun/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  noun.appendChild(Prob_File.createTextNode(total));  
  
  pronoun=Prob_File.createElement("Pronoun");
  other_tps.appendChild(pronoun);
  total=Float.toString(other_tp.nun/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  pronoun.appendChild(Prob_File.createTextNode(total));
  
  adjective=Prob_File.createElement("Adjective");
  other_tps.appendChild(adjective);
  total=Float.toString(other_tp.nun/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  adjective.appendChild(Prob_File.createTextNode(total));
  
  adverb=Prob_File.createElement("Adverb");
  other_tps.appendChild(adverb);
  total=Float.toString(other_tp.nun/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  adverb.appendChild(Prob_File.createTextNode(total));
  
  article=Prob_File.createElement("Article");
  other_tps.appendChild(article);
  total=Float.toString(other_tp.nun/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  article.appendChild(Prob_File.createTextNode(total));
  
  conjunction=Prob_File.createElement("Conjunction");
  other_tps.appendChild(conjunction);
  total=Float.toString(other_tp.nun/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  conjunction.appendChild(Prob_File.createTextNode(total));
  
  interjection=Prob_File.createElement("Interjection");
  other_tps.appendChild(interjection);
  total=Float.toString(other_tp.nun/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  interjection.appendChild(Prob_File.createTextNode(total));
  
  preposition=Prob_File.createElement("Preposition");
  other_tps.appendChild(preposition);
  total=Float.toString(other_tp.ppt/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  preposition.appendChild(Prob_File.createTextNode(total));
  
  verb=Prob_File.createElement("Verb");
  other_tps.appendChild(verb);
  total=Float.toString(other_tp.vrb/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  verb.appendChild(Prob_File.createTextNode(total));
  
  other=Prob_File.createElement("Others");
  other_tps.appendChild(other);
  total=Float.toString(other_tp.oth/(other_tp.nun+other_tp.pnu+other_tp.ppt+other_tp.art+other_tp.vrb+other_tp.adj+other_tp.adv+other_tp.intj+other_tp.con+other_tp.oth));
  other.appendChild(Prob_File.createTextNode(total));
    
    
    
      // write the content into xml file
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DOMSource source = new DOMSource(Prob_File);
    StreamResult result = new StreamResult(new File("Training_Data_tp_data.xml"));
    transformer.transform(source, result);
    }
    catch(Exception ee)
     {
        System.out.println("Exception of add_transitional_probability_to_xml_file:   "+ee);
     }
    
   }
   
  
  
  
  public static void read_training_data_line_by_line()
  {
    try
    {
      
      String[] words=new String[100];
      int i;
      File input_file=new File("training_data.txt");
      InputStream fis = new FileInputStream("training_data.txt");
      InputStreamReader isr = new InputStreamReader(fis);
      BufferedReader br = new BufferedReader(isr);
      String line=new String();
      while((line=br.readLine())!=null&&line.length()>0&&line!="")
      {
        words[0]=line;
        for(i=1;i<100;i++)
        {
          if((line=br.readLine())!=null)
          words[i]=line;
          else
          break;
          
        }
        int n=i-1;
        for(i=0;i<n;i++){
        data_block[i]=new pair(words[i]);
      }
        calculate_count_n_tp(n);
      }
      add_transitional_probability_to_xml_file();
    }
    catch(Exception ee)
    {
      //System.out.println("Exception of read_training_data_line_by_line:   "+ee);
    	return;
    }
  }
  
  
  public static String check_max_emmission_prob(word_tag wrd)
   {
     String posTag=new String();
     float[] posTags=new float[10];
     posTags[0]=wrd.nun;
     posTags[1]=wrd.pnu;
     posTags[2]=wrd.adj;
     posTags[3]=wrd.adv;
     posTags[4]=wrd.art;
     posTags[5]=wrd.con;
     posTags[6]=wrd.intj;
     posTags[7]=wrd.ppt;
     posTags[8]=wrd.vrb;
     posTags[9]=wrd.oth;
     float totalcount=0;
     for(int i=0;i<10;i++)
       totalcount+=posTags[i];
     int max_index=0;
     for(int i=0;i<10;i++)
     {
       if(posTags[max_index]<posTags[i])
         max_index=i;
     }
     switch(max_index)
     {
     case 0:posTag="Noun";
            break;
     

     case 1:posTag="Pronoun";
            break;

     case 2:posTag="Adjective";
            break;

     case 3:posTag="Adverb";
            break;

     case 4:posTag="Article";
            break;

     case 5:posTag="Conjunction";
            break;

     case 6:posTag="Interjection";
            break;

     case 7:posTag="Preposition";
            break;

     case 8:posTag="Verb";
            break;

     case 9:posTag="Others";
            break;
            
     }
     return posTag;
   }
   
   
   public static void write_in_output_file(pair wordNtag)
   {
    try {

      String content = wordNtag.key+" : "+wordNtag.value;

      File file = new File("output.txt");

      // if file doesnt exists, then create it
      if (!file.exists()) {
        file.createNewFile();
      }

      FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.write(content);
      bw.newLine();
      bw.close();

      

    } catch (IOException e) {
      e.printStackTrace();
      
      System.out.println("Exception of write_in_output_file:   "+e);
      return;
    }
   }
   
   
  
  
  public static void find_emmission_probability(String[] words)
   {
     int i=0;
     word_tag wrd=new word_tag();
     pair wordNtag=new pair();
     
     while(words[i]!=null)
     {
       wordNtag.key=words[i];
       wrd=retrive_word_data_from_xml(words[i]);
       wordNtag.value=check_max_emmission_prob(wrd);
       write_in_output_file(wordNtag);
       i++;
     }
   }
   
   
  
  
  
  
  public static void read_test_data_line_by_line()
   {
     String line;

     try {
         InputStream fis = new FileInputStream("Testing_data.txt");
         InputStreamReader isr = new InputStreamReader(fis);
         BufferedReader br = new BufferedReader(isr);
         
         while ((line = br.readLine()) != null&&line.length()>0&&line!="")
         {
         String[] words = line.split(" ");
         
         find_emmission_probability(words);
         // Now you have a String array containing each word in the current line
          }
      }
     catch(Exception ee)
    {
      //System.out.println("Exception of read_test_data_line_by_line:  "+ee);
    	 return;
    }
     
     
   }
   
public static void create_training_data_tp_xml_file()
{
	try {

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("Words");
		doc.appendChild(rootElement);
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File("Training_Data_tp_data.xml"));//C:\\Users\\Welcome\\Desktop\\POS-Tagging\\Training_Data_tp_data.xml"));

		// Output to console for testing
		// StreamResult result = new StreamResult(System.out);

		transformer.transform(source, result);

		System.out.println("File saved!");

	  } catch (ParserConfigurationException pce) {
		pce.printStackTrace();
	  } catch (TransformerException tfe) {
		tfe.printStackTrace();
	  }
}
  
  
  public static void main(String agr[])
  {
	 create_training_data_tp_xml_file();
	 System.out.println("\n****************************training start**************************\n\n");
     read_training_data_line_by_line();
     System.out.println("\n****************************training over**************************\n\n");
    try {
        Thread.sleep(2000);                 //1000 milliseconds is one second.
    } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
    }
    System.out.println("\n****************************testing start**************************\n\n");
  read_test_data_line_by_line();
  System.out.println("\n****************************testing over**************************\n\n");
  }
}  
  