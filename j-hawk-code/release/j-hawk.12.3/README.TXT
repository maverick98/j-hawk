How to install hawk in ubuntu
---------------------------------

1) Execute install.sh
   sudo install.sh


How to install hawk in windows
---------------------------------

1) Execute install.cmd
   install.cmd



How to use SampleApp 
---------------------------------

1) Open a shell window
2) Go to SampleApp directory   
2) hawk -s ./setting.conf -training
   This will give the o/p as below.
 		     HttpModule
                            |
                            |
                            |------->Wait()
                            |------->Execute(var hawkStruct)
                            |------->Reset()


                     MathModule
                            |
                            |
                            |------->Sqrt(var number)
                            |------->Asin(var number)
                            |------->Atan(var number)
                            |------->Abs(var number)
                            |------->Sinh(var number)
                            |------->Acos(var number)
                            |------->Floor(var number)
                            |------->Sin(var number)
                            |------->Ceil(var number)
                            |------->Exp(var number)
                            |------->Cos(var number)
                            |------->Log(var number)
                            |------->Cosh(var number)


                     OddModule
                            |
                            |
                            |------->CheckOdd(var number)


                     PrimeModule
                            |
                            |
                            |------->CheckPrime(var number)


                     SystemModule
                            |
                            |
                            |------->Sort(var array)
                            |------->DumpStack()
                            |------->CurrentTime()
                            |------->Length(var array)
                            |------->Execute(var command)


                     FileModule
                            |
                            |
                            |------->Write(var array)
                            |------->Read(var array,var filename)
                            |------->Delete(var filename)
                            |------->List(var array,var filename)


                     EvenModule
                            |
                            |
                            |------->CheckEven(var number)


EvenModule , OddModule , PrimeModule are SampleApp's implementations of j-hawk framework's IModule contract.

For more help on hawk, please refer to hawk's manual page.

man hawk


=========================================








