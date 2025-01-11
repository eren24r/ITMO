module math_accelerator (
    input wire clk_i,           // �������� ������
    input wire rst_i,           // ������ ������
    input wire start_i,         // ������ �������
    input wire [7:0] a,         // 8-������ �������� a
    input wire [7:0] b,         // 8-������ �������� b
    output reg busy_o,          // ���� ���������
    output reg [15:0] y         // 16-������ ��������� y = a * sqrt(b)
);

    reg [7:0] sqrt_result;      // ��������� ���������� sqrt(b)
    reg [7:0] x;                // ������� ����������� sqrt(b)
    reg [7:0] x_next;           // ��������� �����������
    reg [15:0] temp1;           // ��������� ���������� ��� x^2
    reg signed [31:0] temp2;    // ��������� ���������� ��� �������
    integer i;                  // ������� ��������

    wire mult_busy;             // ������ ��������� ����������
    reg mult_start;             // ������ ������� ����������
    wire [15:0] mult_result;    // ��������� ���������

    // ������ ���������
    mult multiplier (
        .clk_i(clk_i),
        .rst_i(rst_i),
        .a_bi(a),
        .b_bi(sqrt_result),
        .start_i(mult_start),
        .busy_o(mult_busy),
        .y_bo(mult_result)
    );

    // ��������� �������
    reg [1:0] state;
    localparam IDLE = 2'b00, CALC_SQRT = 2'b01, CALC_MULT = 2'b10, DONE = 2'b11;

    // ������ ���������
    always @(posedge clk_i or posedge rst_i) begin
        if (rst_i) begin
            state <= IDLE;
            busy_o <= 0;
            y <= 0;
            mult_start <= 0;
            sqrt_result <= 0;
            x <= 0; // ������������� ���������� �����������
        end else begin
            case (state)
                IDLE: begin
                    busy_o <= 0;
                    mult_start <= 0;
                    if (start_i) begin
                        busy_o <= 1;
                        x <= (b >> 2) + 1; // ��������� ����������� sqrt(b)
                        state <= CALC_SQRT;
                    end
                end

                CALC_SQRT: begin
                    // ��������� ���������� ������ ����� ��������
                    for (i = 0; i < 15; i = i + 1) begin
                        temp1 = x * x; // ������� �������� �����������
                        temp2 = $signed({1'b0, b}) - $signed(temp1); // �������
                        if (temp2 >= 0) begin
                            x_next = x + (temp2 / (2 * x + 1)); // ���������
                        end else begin
                            x_next = x - (x >> 1); // ����������
                        end
                        if (x_next > 255) x_next = 255;
                        if (x_next < 0) x_next = 0;
                        x = x_next;
                    end
                    sqrt_result <= x; // ��������� ��������� sqrt(b)
                    state <= CALC_MULT;
                end

                CALC_MULT: begin
                    // ������ ���������
                    if (!mult_busy && !mult_start) begin
                        mult_start <= 1;
                    end else if (mult_busy) begin
                        mult_start <= 0;
                    end

                    // ���������, ��������� �� ���������
                    if (!mult_busy && mult_start == 0) begin
                        y <= mult_result; // ��������� ��������� ���������
                        state <= DONE;
                    end
                end

                DONE: begin
                    busy_o <= 0;      // ������� ���� ���������
                    state <= IDLE;    // ������� � ��������
                end
            endcase
        end
    end
endmodule
