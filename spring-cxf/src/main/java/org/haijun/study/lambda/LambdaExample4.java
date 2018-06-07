package org.haijun.study.lambda;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * 文件读写
 */
public class LambdaExample4 {


    // list.Stream.parallel() 并行的流
    public static void main(String[] args) {
        Path path = Paths.get("G:\\Temp.txt");

        try (BufferedWriter writer = Files.newBufferedWriter(path))
        {
            writer.write("Hello World !!");
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        // Using Lines
        try(Stream<String> stream = Files.lines(path,Charset.defaultCharset())){
            stream.forEach(System.out::println);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Using newBufferedReader
        try(BufferedReader br = Files.newBufferedReader(path)){
            Stream<String> stream = br.lines();
            stream.forEach(System.out::println);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try(Stream<String> stream = Files.lines(path)){
            // print only if line is not blank
            stream.filter(line->!line.trim().equals(""))
                    .forEach(System.out::println);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
