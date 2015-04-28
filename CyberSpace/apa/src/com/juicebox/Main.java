package com.juicebox;

import javax.swing.*;

public class Main {

    //defaults
    private static double min_peak_dist = 30; //distance between two bins, can be changed in opts
    private static double max_peak_dist = Double.POSITIVE_INFINITY;
    private static int window = 10;
    private static int width = 6; //size of boxes
    private static int peakwidth = 2; //for enrichment calculation of crosshair norm
    private static double res = Math.pow(10,4);
    private static boolean save_all = false;

    // required from arguments
    private static String counts_folder, peaks_filename, save_folder, save_prefix, restriction_site_filename;


    private static void parse_flags_arguments(String[] args){
        int index = 0;

        while(true){
            String flag = args[index];
            String flag_value = args[index+1]; // assumed flag value; not used for -s and -h

            if(flag.charAt(0) == '-'){
                switch(flag.charAt(1)){
                    case 'w' :
                        window=Integer.parseInt(flag_value);
                        break;
                    case 'm' :
                        min_peak_dist= Double.parseDouble(flag_value);
                        break;
                    case 'x' :
                        max_peak_dist= Double.parseDouble(flag_value);
                        break;
                    case 'r' :
                        res = Double.parseDouble(flag_value);
                        break;
                    case 's' :
                        save_all = true;
                        index--;
                        break;
                    default :
                        System.out.println("Correct usage:");
                        printArgumentHelp();
                        System.exit(707);
                }
                index += 2;
            }
            else{
                break;
            }
        }

        // the flags have been handled
        // now attempt to run th code
        // Parameters (non optional)


        try {
            counts_folder = args[index];
            peaks_filename = args[index + 1];
            save_folder = args[index + 2];
            save_prefix = args[index + 3];
            if(args.length > index + 4 ){
                restriction_site_filename = args[4];
            }
            else{
                restriction_site_filename = "/broad/aidenlab/restriction_sites/hg19_HindIII.txt";
            }
        }
        catch (Exception e){
            System.out.println("Help on usage:");
            printArgumentHelp();
            System.exit(702);
        }
    }

    private static void printArgumentHelp(){
        System.out.println("<-m minval> <-x max> <-w window>  <-r res> <-s>" +
                " CountsFolder PeaksFile/PeaksFolder SaveFolder SavePrefix ");
        System.out.println("-m      Minimum peak distance (i.e. distance between two bins)");
        System.out.println("-x      Maximum peak distance (i.e. distance between two bins)");
        System.out.println("-w      Window size");
        System.out.println("-r      Hi-C map resolution size");
        System.out.println("-s      Save all data");

    }

     private static JFileChooser chooser;

    public static void main (String[] args) {
        //parse_flags_arguments(args);

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("temp title");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    +  chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    +  chooser.getSelectedFile());
        }
        else {
            System.out.println("No Selection ");
        }
    }
}
