package com.hrzafer.fatihparser.util;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * Dosya işlemleri sınıfı.
 *
 * @author Yazar: Harun Reşit Zafer - hrzafer@gmail.com hrzafer.com
 */
public class IO {

    /**
     * Diskten bir kalemde okuyacağımız bayt miktarı
     */
    private static final int BUFFER = 8192;
    /**
     * Unicode karakter kodu - "UTF-8"
     */
    public static final String UTF_8 = "UTF-8";
    /**
     * ISO Türkçe karakter kodu - "ISO-8859-9"
     */
    public static final String ISO_TR = "ISO-8859-9";
    public static final String NEW_LINE = "\r\n";

    /**
     * Bir dosyanın belirtilen adreste olup olmadığını döndürür<br/> Ör: IO.exists("dosyalarım/dosyam.txt");
     */
    public static boolean exists(String file) {
        boolean exists = (new File(file)).exists();
        return exists;
    }

    /**
     * Bir dosyayı okuyup bütünüyle string olarak döndürür. Dosya Unicode biçiminde ise sorunsuz çalışır. Aksi halde
     * Türkçe karakterlerde sorun yaşamamak için (Dosya Türkçe karakterler içeriyorsa ve Unicode biçiminde değilse):
     * read("beni_oku.txt", "ISO-8859-9") şeklinde kullanılmalıdır
     * <br><br>Not: Yine de Türkçe karakter içeren dosyalarınızı her zaman unicode biçiminde kaydetmeniz tavsiye edilir.
     */
    public static String read(String file) {
        return read(file, UTF_8);
    }

    /**
     * Bir dosyayı okuyup bütünüyle string olarak döndürür. Türkçe karakter içeren ANSI biçiminde dosyalar için:
     * read("beni_oku.txt", "ISO-8859-9") şeklinde çağırılması tavsiye edilir Kaynak:
     * http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file/326440#326440
     */
    public static String read(String file, String encoding) {
        // No real need to close the BufferedReader/InputStreamReader
        // as they're only wrapping the stream
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(file);
            Reader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName(encoding)));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[BUFFER];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(stream);
        }
    }

    public static String read(InputStream is) {
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName(UTF_8)));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[BUFFER];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Kaynak dosyayı (from) hedef dosyaya (to) kopyalar. Klasörler için kullanılmaz! <br/> Ör: copy("beni_kopyala.txt",
     * "bana_kopyala.txt");
     */
    public static void copy(String from, String to) {
        File source = new File(from);
        File target = new File(to);
        copy(source, target);
    }

    /**
     * Kaynak(source) dosyayı hedef(target) dosyaya kopyalar. Klasörler için kullanılmaz!<br/> Ör: copy(new
     * File("beni_kopyala.txt"), new File("bana_kopyala.txt"));
     */
    public static void copy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;

        try {
            in = new FileInputStream(source).getChannel();
            out = new FileOutputStream(target).getChannel();

            ByteBuffer buffer = ByteBuffer.allocateDirect(BUFFER);
            while (in.read(buffer) != -1) {
                buffer.flip();

                while (buffer.hasRemaining()) {
                    out.write(buffer);
                }

                buffer.clear();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(in);
            close(out);
        }
    }

    /**
     * Bir String'i bütünüyle dosyaya yazar.<br/> Ör: write("bana_yaz.txt", "beni dosyaya yaz");
     */
    public static void write(String filePath, String content) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(content);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(writer);
        }
    }

    /**
     * String'i Base64 biçiminde kodlayarak dosyaya yazar. Böylece dosya insanlar tarafından okunamayacak biçimde
     * kaydedilir. Bu biçimdeki dosyayı readBase64 metod ile okumak gerekir.
     */
    public static void writeBase64(String filePath, String content) {
        byte[] bytes = content.getBytes(Charset.forName(UTF_8));
        String base64str = Base64.encode(bytes);
        write(filePath, base64str);
    }

    /**
     * Base64 biçiminde kodlanarak yazılmış bir dosyayı okur ve String olarak döndürür.
     *
     */
    public static String readBase64(String filePath) {
        String str = read(filePath);
        byte[] decodedBytes = Base64.decode(str);
        return new String(decodedBytes, Charset.forName(UTF_8));
    }

    /**
     * Bir dosyadaki satırları liste (ArrayList) olarak döndürür.
     */
    public static List<String> readLines(String filePath) {
        return readLines(filePath, UTF_8);
    }

    /**
     * Bir dosyadaki satırları liste (ArrayList) olarak döndürür.
     */
    public static List<String> readLines(String filePath, String encoding) {
        Scanner scanner = new Scanner(read(filePath, encoding));
        List<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        scanner.close();
        return lines;
    }

    /**
     * Bir dosyadaki satırları sonlarıdaki new_line karakterleri ile birlikte okur ve liste (ArrayList) olarak döndürür.
     */
    public static List<String> readLinesIncludingEmptyLines(String filePath, String encoding) {
        Scanner scanner = new Scanner(read(filePath, encoding));
        List<String> lines = new ArrayList<String>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            lines.add(line + NEW_LINE);
        }
        scanner.close();
        return lines;
    }

    public static List<String> readLinesIncludingEmptyLines(String filePath) {
        return readLinesIncludingEmptyLines(filePath, UTF_8);
    }

    /**
     * Bir listedeki string'leri dosyaya yazar.
     */
    public static void write(String filePath, List<String> lines) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            for (String line : lines) {
                writer.write(line);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(writer);
        }
    }

    /**
     * Listedeki her bir String'in sonuna bir satır sonu karakteri "\r\n" eklenir <br/> ve dosyaya yazılır.
     */
    public static void writeLines(String filePath, List<String> lines) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filePath));
            for (String line : lines) {
                writer.write(line + NEW_LINE);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(writer);
        }
    }

    /**
     * String'i dosya gibi okuyabilmek için kullanılabilecek bir metod.
     */
    public static InputStream toInputStream(String source) {
        InputStream stream;
        try {
            stream = new ByteArrayInputStream(source.getBytes(UTF_8));
            return stream;
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Dosyayı exception handling ile uğraşmadan kapatmak için.
     */
    private static void close(Closeable closable) {
        if (closable != null) {
            try {
                closable.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    /**
     * Bir dosyayı okuyup bütünüyle string olarak döndürür. Türkçe karakter içeren ANSI biçiminde dosyalar için:
     * read("beni_oku.txt", "ISO-8859-9") şeklinde çağırılması tavsiye edilir Kaynak:
     * http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file/326440#326440
     */
    public static String readResource(String resourcePath) {
        // No real need to close the BufferedReader/InputStreamReader
        // as they're only wrapping the stream
        InputStream stream = null;
        try {
            stream = IO.class.getResourceAsStream(resourcePath);
            Reader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName(UTF_8)));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[BUFFER];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            close(stream);
        }
    }

    /**
     * *.properties uzantılı özellik dosyalarını okuyup, Properties nesneni olarak döndürür. Dosyanın paketler
     * içerisindeki tam konumu verilmelidir. Mesela db.properties dosyası default pakette ise /db.properties olarak,
     * com.hrzafer.resources gibi bir konumda ise /com/hrzafer/resources/db.properties olarak.
     */
    public static Properties readProperties(String filepath) {
        return readProperties(filepath, false);
    }

    public static Properties readProperties(String filepath, boolean outsideJar) {
        InputStream in;
        try {
            if (outsideJar) {
                in = new FileInputStream(filepath);
            } else {
                in = IO.class.getResourceAsStream(filepath);
            }
            return readProperties(in);
        } catch (Exception ex) {
            throw new RuntimeException("Properties file (" + filepath + ") can not be read!!!", ex);
        }
    }

    private static Properties readProperties(InputStream in) throws IOException {
        Properties properties = new Properties();
        properties.load(in);
        in.close();
        return properties;
    }
}
