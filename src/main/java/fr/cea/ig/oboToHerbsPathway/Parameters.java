package fr.cea.ig.oboToHerbsPathway;

import com.lexicalscope.jewel.cli.Option;

public interface Parameters {
    @Option(shortName = "h", longName = "help", helpRequest = true)
    boolean getHelp();
    
    @Option( shortName = "i", longName = "input", description = "Path to obo file which describe the pathways" )
    String getOboFile(); 
    
    @Option( shortName = "t", longName = "term", description = "Pathway to convert to herbs byusing  their terms id")
    String getTermId();
    
    @Option( shortName = "o", longName = "output", description = "Herbs file to generate" )
    String getHerbsFile();
    
    @Option( shortName = "u", longName = "unix", description = "Use unix line termination \\n" )
    boolean useUnixEndLine();

}
