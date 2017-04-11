import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class encrypt {
	private int count;
	private static int min = 128;
	private static int max = 254;
	
	private static String folder = "";
	private static String newfolder = "";
	
    public static void main(String[] args) throws Exception {
    	String path = "C:\\Documents and Settings\\user\\Desktop\\";
    	folder = "yomeData";
    	newfolder = folder+"_out";
    	
    	getFolder(path+folder);
    	
        System.out.println("...all done");
    }
    
    public static void getFolder(String outFolder) {
    	System.out.println("processing folder "+outFolder+"...");
    	String[] str = {"png","mp3"};
    	File dir = new File(outFolder);
    	File[] files = dir.listFiles();
        File dirFile  = null ;
        try {
            dirFile  =   new  File(outFolder.replaceFirst(folder, newfolder));
             if ( ! (dirFile.exists())  &&   ! (dirFile.isDirectory())) {
                 boolean  creadok  =  dirFile.mkdirs();
                 if (creadok) {
                } else {                
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            System.out.println(e);
//             return   false ;
        } 
    	for(int i=0;i<files.length;i++){
    		if (files[i].isDirectory()){
    			getFolder(files[i].getPath());
    		}
    		else{
	    		String fileType = files[i].getName().substring(files[i].getName().lastIndexOf('.')+1,files[i].getName().length());
	    		for(int t=0;t<str.length;t++){
	    			if(str[t].equals(fileType.toLowerCase())){
	    				String name = files[i].getPath();
	    				String name2 = files[i].getPath().replaceFirst(folder, newfolder);
	    				try{
	    					fixPng(name, name2);
	    				}catch (Exception e) {
	    					
	    				}
	    			}
	    		}
    		}
    	}
    }
	public static void fixPng(String inputFile, String outputFile) throws IOException{
		FileInputStream inputStream=new FileInputStream(inputFile);
		DataOutputStream out=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(outputFile)));
		
		int input;
		input = inputStream.read();
		int output = 0;
		int i = 0;
		int add = i;
		int[] Mask = new int[max-min+1];
		for (i = min;i<=max; i++){
			Mask[i-min]= i;
		}
		i = 0;
		while (input != -1){
			String string1 = Integer.toBinaryString(input);
			while (string1.length() < 8){
				string1 = "0" + string1;
			}
			String string2 = Integer.toBinaryString(Mask[i]);
			while (string2.length() < 8){
				string2 = "0" + string2;
			}
			output = 0;
			add = 128;
			for (int j = 0; j < 8; j++){
				if (string1.charAt(j)!=string2.charAt(j)){	output = output + add;}
				add = add / 2;
			}
			out.writeByte(output);
			i += 1;
			if (i == Mask.length){
				i = 0;
			}
			input = inputStream.read();
		}
		inputStream.close();
		out.close();
	}
}
