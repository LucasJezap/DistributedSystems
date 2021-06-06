using System;
using Ice;
using Demo;
using System.Collections.Generic;
using System.Threading;
using System.Globalization;

namespace Client
{
    class Client
    {
        private static void test1(Communicator communicator, ObjectPrx proxy, float x1, float y1, float x2, float y2)
        {
            Line line = new Line();
            line.x1 = x1;
            line.y1 = y1;
            line.x2 = x2;
            line.y2 = y2;
            OutputStream outputStream = new OutputStream(communicator);
            outputStream.startEncapsulation();
            Line.ice_write(outputStream, line);
            outputStream.endEncapsulation();
            byte[] inParams = outputStream.finished();
            byte[] outParams;
            if (proxy.ice_invoke("lineLength", OperationMode.Normal, inParams, out outParams))
            {
                InputStream inputStream = new InputStream(communicator, outParams);
                inputStream.startEncapsulation();
                float lineLength = inputStream.readFloat();
                inputStream.endEncapsulation();
                Console.WriteLine("Length of line (" + line.x1 + "," + line.y1 + ") -> (" +
                    line.x2 + "," + line.y2 + ") is " + lineLength + ".");
            }
        }

        private static void test2(Communicator communicator, ObjectPrx proxy, float[] seq)
        {
            OutputStream outputStream = new OutputStream(communicator);
            outputStream.startEncapsulation();
            outputStream.writeFloatSeq(seq);
            outputStream.endEncapsulation();
            byte[] inParams = outputStream.finished();
            byte[] outParams;
            if (proxy.ice_invoke("squareSequence", OperationMode.Normal, inParams, out outParams))
            {
                InputStream inputStream = new InputStream(communicator, outParams);
                inputStream.startEncapsulation();
                float[] squares = inputStream.readFloatSeq();
                inputStream.endEncapsulation();
                Console.WriteLine("The square sequence of sequence [" +
                    String.Join(",", seq) + "] is [" + String.Join(",", squares) + "].");
            }
        }

        private static void test3(Communicator communicator, ObjectPrx proxy, string sentence)
        {
            OutputStream outputStream = new OutputStream(communicator);
            outputStream.startEncapsulation();
            outputStream.writeString(sentence);
            outputStream.endEncapsulation();
            byte[] inParams = outputStream.finished();
            byte[] outParams;
            if (proxy.ice_invoke("countLetters", OperationMode.Normal, inParams, out outParams))
            {
                InputStream inputStream = new InputStream(communicator, outParams);
                inputStream.startEncapsulation();
                Dictionary<string, int> squares = LetterCountHelper.read(inputStream);
                inputStream.endEncapsulation();
                Console.WriteLine("The counted letters of sentence '" + sentence + "': {\n" + string.Join(Environment.NewLine, squares) + "\n}.");
            }
        }

        static void Main(string[] args)
        {
            Thread.CurrentThread.CurrentCulture = CultureInfo.GetCultureInfo("en-US");
            Communicator communicator = Util.initialize();
            ObjectPrx proxy = communicator.stringToProxy("calc/calc11:tcp -h 127.0.0.2 -p 10000 -z : udp -h 127.0.0.2 -p 10000 -z");
            if (proxy == null)
            {
                throw new System.Exception("some exception line 13");
            }

            test1(communicator, proxy, 15.0f, 13.2f, 11.9f, 17.8f);
            test2(communicator, proxy, new float[] { 1, 91, 182, 923});
            test3(communicator, proxy, "I love ice cream and chocolate");

            Console.WriteLine("Click Enter to exit.");
            Console.ReadLine();
        }
    }
}