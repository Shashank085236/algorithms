package greedy;

/**
 * Created by shashank.dwivedi on 1/23/2018.
 *
 *
 * Huffman coding is a lossless data compression algorithm.
 * The idea is to assign variable-length codes to input characters,
 * such that length of assigned code is inversaly proportional to frequency of characrer.
 * Variable length codes are prefix code! meaning prefix of any code is not code for any character. this prevents ambiguity.
 * Example:
 * Let there be four characters a, b, c and d, and their corresponding variable length codes be 00, 01, 0 and 1.
 * This coding leads to ambiguity because code assigned to c is prefix of codes assigned to a and b.
 * If the compressed bit stream is 0001, the de-compressed output may be “cccd” or “ccb” or “acd” or “ab”.
 *
 * Huffman coding requires all characters to be known beforehand. For live data, adaptive Huffman coding can be used.
 *
 * Steps:
 * - Build Huffman tree
 * - traverse tree to to create codes
 *
 */
public class HuffmanCoding {


    HuffmanCoding c = new HuffmanCoding();


}
