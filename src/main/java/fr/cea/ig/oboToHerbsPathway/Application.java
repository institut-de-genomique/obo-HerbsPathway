package fr.cea.ig.oboToHerbsPathway;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.lexicalscope.jewel.JewelRuntimeException;
import com.lexicalscope.jewel.cli.Cli;
import com.lexicalscope.jewel.cli.CliFactory;

import fr.cea.ig.obo.Parser;
import fr.cea.ig.obo.model.Relation;
import fr.cea.ig.obo.model.Term;
import fr.cea.ig.obo.model.TermRelations;
import fr.cea.ig.obo.model.UCR;
import fr.cea.ig.obo.model.UER;
import fr.cea.ig.obo.model.Variant;

public final class Application {


    private static String variantsToPathwayClips( final List<Variant> variants, final String pathway ){
        StringBuilder   str     = new StringBuilder();
        Integer         stepNum = 0;
        for( ; stepNum < variants.size(); stepNum++ )
            str.append( pathway + "-alt-" + stepNum.toString() + " " );
        return str.toString();
    };


    private static void processWriter( BufferedWriter writer, final String process, final List<Variant> variants) {
        Integer         stepNum = 0;
        try {
            for( ; stepNum < variants.size(); stepNum++ )
                writer.write( "(process define "+ process + "-alt-" + stepNum.toString() + "  -> and " + Tools.join( Tools.replace( variants.get(stepNum).getTermId(), "UPa:", "" ) , " ") + ")\n" );
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }


    private static void pathwayWriter(  BufferedWriter writer, final String process, Term term, final Class<? extends Term> untilItem ){
            Integer         stepNum = 0;
            List<Variant>   variants= new ArrayList<Variant>();
            Variant.getVariant( ((TermRelations)term).getChilds() , variants);
            if( ! untilItem.isInstance(term) ){
                if(  variants.size() != 0){
                    try {
                        final String    logic   = ( variants.size() > 1 )? "or" : "and";
                        writer.write("(process define "+ process +" -> "+logic+" " + variantsToPathwayClips(variants, process) + ")\n" ) ;
                        processWriter( writer, process, variants );
                        for( Variant variant : variants ){
                            
                            for( Term child : variant)
                                pathwayWriter(  writer, child.getId().replace("UPa:", ""), child, untilItem );
                            
                            stepNum++;
                        }
                        
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else if( term instanceof UCR ){
                        try {
                            Set<Relation>      partOf = ((UCR)term).getRelation("has_input_compound");
                            String[]           ids    = new String[ partOf.size() ];
                            Iterator<Relation> iter    = partOf.iterator();
                            for( int i = 0; i < ids.length ; i++ ){
                                ids[ i ] = iter.next().getIdLeft().replace( "UPa:", "" );
                            }
                            writer.write( "(process define "+ process + "  -> and " + Tools.join( ids , " ") + ")\n" );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }
    }


    public static void main(String[] args) throws ParseException, IOException {
        final Cli<Parameters>   cli     = CliFactory.createCli( Parameters.class );
        Parameters              params  = null;
        
        try {
            params = CliFactory.parseArguments(Parameters.class, args);
        } catch( JewelRuntimeException e){
            System.err.println( "Error while parsing command line:" );
            System.err.println( e.getMessage( ) );
            System.err.println();
            System.out.println( cli.getHelpMessage() );
            System.exit(1);
        }
        
        Parser          parser  = new Parser( params.getOboFile() );
        
        if( params.useUnixEndLine() )
            System.setProperty("line.separator", "\n");
        
        TermRelations   root    = ( TermRelations ) parser.getTerm( params.getTermId() );
        String          pathway = root.getId().replace("UPa:", "");
        BufferedWriter  writer  = new BufferedWriter(
                                            new OutputStreamWriter(
                                                        new FileOutputStream(params.getHerbsFile() ),
                                                        Charset.forName("US-ASCII") ), 4 * 4096 );
        
        final String pathwayName = params.getHerbsFile().substring(0, params.getHerbsFile().lastIndexOf('.'));
        
        writer.write(
                        ";;;; -------------------------------------------------------\n"    +
                        ";;; HERBS (Hamap Expert Rules Based System)\n"                     +
                        ";;;\n"                                                             +
                        ";;; @file: " + params.getHerbsFile() + "\n"                        +
                        ";;; -------------------------------------------------------\n"     +
                        ";;;\n"                                                             +
                        "(process declare "+ pathwayName +" present in ALL)\n"              +
                        "(process define "+ pathwayName +" -> and "+ pathway +")\n"                  );

        pathwayWriter(  writer, pathway, root, UER.class );
        
        writer.close();
    }

}

