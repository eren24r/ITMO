module testbench;
    // ����� ��� math_accelerator
    reg clk_i;                // �������� ������
    reg rst_i;                // ������ ������
    reg start_i;              // ������ �������
    reg [7:0] a = 2;              // 8-������ �������� a
    reg [7:0] b = 4;              // 8-������ �������� b

    // ����� math_accelerator
    wire busy_o;              // ���� ���������
    wire [15:0] y;            // 16-������ �������� ����������

    // ������������ math_accelerator
    math_accelerator uut (
                    .clk_i(clk_i),
                    .rst_i(rst_i),
                    .start_i(start_i),
                    .a(a),
                    .b(b),
                    .busy_o(busy_o),
                    .y(y)
                );
                
    // ��������� ��������� �������
    initial clk_i = 0;
    always #5 clk_i = ~clk_i; // ������� 100 ��� (������ 10 ��)

    // ��������� ������������
    initial begin
        // ����� �������
        rst_i = 1;
        start_i = 0;
        #10; // ��� 20 ��
        rst_i = 0;
        
        #20
        

        // �������� �����
    
        run_test(8'd2, 8'd4);
         run_test(8'd3, 8'd9);   // sqrt(9) = 3, y = 3 * 3 = 9
 run_test(8'd4, 8'd16);  // sqrt(16) = 4, y = 4 * 4 = 16
 run_test(8'd5, 8'd25);  // sqrt(25) = 5, y = 5 * 5 = 25
 run_test(8'd6, 8'd36);  // sqrt(36) = 6, y = 6 * 6 = 36
 run_test(8'd7, 8'd49);  // sqrt(49) = 7, y = 7 * 7 = 49
 run_test(8'd8, 8'd64);  // sqrt(64) = 8, y = 8 * 8 = 64
 run_test(8'd10, 8'd100); // sqrt(100) = 10, y = 10 * 10 = 100
 run_test(8'd12, 8'd144); // sqrt(144) = 12, y = 12 * 12 = 144
 run_test(8'd15, 8'd225); // sqrt(225) = 15, y = 15 * 15 = 225   // sqrt(4) = 2, y = 2 * 2 = 4
         // sqrt(64) = 8, y = 8 * 8 = 64
         
       
         $finish;
   // ��������� ���������
    end



                
    // ��������� ������� �����
    task run_test(reg [7:0] test_a, reg [7:0] test_b);
                
        begin 
                    
          
                    a = test_a;
                    b = test_b;
                    
                    start_i = 1;
                    #10
                    start_i = 0;
                    
                  // ���������� start ��������
                    

        
                    // ���, ���� ���������� ������ (busy_o == 1)
                    wait (!busy_o);
                    #10;         // ��������� �������� ��� �������� ����������        
                    
                 
                    
                    
                    $display("Time: %0t | a = %d, b = %d, y = %d", $time, a, b, y);
                        
                            
        end
    endtask
    
endmodule
