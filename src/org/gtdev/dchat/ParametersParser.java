package org.gtdev.dchat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParametersParser {
    //! This is flag handler class to define a function to be called. This function will be
    //! invoked with the number of arguments after the functional flag and the array of them.
    public static class arg_handler {
        public String flag; //! This is the cmd line parameter to handle ("--server").
        public String para_document; //! This is the parameter documentation to show ("<IP Address>").
        public String document; //! This is the easy readable documentation to print.
        public handler Ifunc;
        //! Function interface of handler function.
        public interface handler {
            /**
             * This function should return an integer representing how many parameters were
             * used by the call or negatives to show an error.
             *
             * @param argc The number of parameters.
             * @param argv The array of following parameters.
             * @return The number of parameters that consumed by the call or negative error.
             */
            int func(int argc, String[] argv);
        }
        public arg_handler (String f, String d, String pd, handler h) {
            flag = f;
            para_document = pd;
            document = d;
            Ifunc = h;
        }
    }

    public static void printHelp(arg_handler[] args) {
        System.out.println("DChat command line parameters:");
        for(arg_handler a:args) {
            System.out.println(a.flag+" "+a.para_document);
            System.out.println("\t"+a.document);
        }
    }

    public static void procArgs(arg_handler[] defargs, String[] args) {
        int defargc = defargs.length;
        int argc = args.length;
//        if(argc == 0) {
//            printHelp(defargs);
//            System.exit(0);
//        }
        List<String> argv = new ArrayList<String>(Arrays.asList(args));
        while (argc != 0) {
            if(args[0].equals("--help")) {
                printHelp(defargs);
                System.exit(0);
            } else {
                boolean arg_handled = false;
                for (int i = 0; i < defargc; ++i) {
                    arg_handler a = defargs[i];
                    if(a.flag.equals(argv.get(0))) {
                        argc--;
                        String cur_arg = argv.remove(0);
                        int args_consumed = a.Ifunc.func(argc, argv.toArray(new String[argv.size()]));
                        if (args_consumed < 0) {
                            System.out.println("Failed parsing parameter "+ cur_arg);
                            System.exit(1);
                        }
                        argc -= args_consumed;
                        for(int j=0; j<args_consumed; j++)
                            argv.remove(0);
                        arg_handled = true;
                        break;
                    }
                }
                if (!arg_handled) {
                    argc--;
                    argv.remove(0);
                }
            }
        }
    }
}
