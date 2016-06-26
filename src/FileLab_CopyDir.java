/**
 * Created by Al on 26.06.2016.
 */
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileLab_CopyDir {
    public static void main(String[] args) {
        copy(new File("F:/tmp"), new File("F:/tmp2"));
    }

    private static void copy(File src, File dst) {
        if (src.isDirectory()) {
            if (!dst.exists()) {
                dst.mkdir();
            }
            for (File srcSubDir : src.listFiles()) {
                String subDirName = srcSubDir.getName();
                copy(srcSubDir, new File(dst, subDirName));
            }
        }
        else if(src.isFile()) {
            try {
                FileInputStream in = new FileInputStream(src);
                FileOutputStream out = new FileOutputStream(dst);
                copyFiles(in, out);
                closeQuietly(in, out);
            }catch(IOException e){
                e.printStackTrace();

            }
        }
    }

    public static void copyFiles(FileInputStream in, FileOutputStream out){
        FileChannel fIn = in.getChannel();
        FileChannel fOut = out.getChannel();

        ByteBuffer buf = ByteBuffer.allocateDirect(64 * 1024);
        long len = 0;
        try {
            while ((len = fIn.read(buf)) != -1) {
                buf.flip();
                fOut.write(buf);
                buf.clear();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
     }

    public  static void closeQuietly(FileInputStream in, FileOutputStream out){
       try{
           if(in != null)
               in.close();
           if(out != null)
               out.close();
       }catch(IOException e){
           //NOP
       }

    }
}
