module math_accelerator (
    input wire clk_i,           // Тактовый сигнал
    input wire rst_i,           // Сигнал сброса
    input wire start_i,         // Сигнал запуска
    input wire [7:0] a,         // 8-битное значение a
    input wire [7:0] b,         // 8-битное значение b
    output reg busy_o,          // Флаг занятости
    output reg [15:0] y         // 16-битный результат y = a * sqrt(b)
);

    reg [7:0] sqrt_result;      // Результат вычисления sqrt(b)
    reg [7:0] x;                // Текущее приближение sqrt(b)
    reg [7:0] x_next;           // Следующее приближение
    reg [15:0] temp1;           // Временная переменная для x^2
    reg signed [31:0] temp2;    // Временная переменная для разницы
    integer i;                  // Счётчик итераций

    wire mult_busy;             // Сигнал занятости умножителя
    reg mult_start;             // Сигнал запуска умножителя
    wire [15:0] mult_result;    // Результат умножения

    // Модуль умножения
    mult multiplier (
        .clk_i(clk_i),
        .rst_i(rst_i),
        .a_bi(a),
        .b_bi(sqrt_result),
        .start_i(mult_start),
        .busy_o(mult_busy),
        .y_bo(mult_result)
    );

    // Состояние системы
    reg [1:0] state;
    localparam IDLE = 2'b00, CALC_SQRT = 2'b01, CALC_MULT = 2'b10, DONE = 2'b11;

    // Логика состояния
    always @(posedge clk_i or posedge rst_i) begin
        if (rst_i) begin
            state <= IDLE;
            busy_o <= 0;
            y <= 0;
            mult_start <= 0;
            sqrt_result <= 0;
            x <= 0; // Инициализация начального приближения
        end else begin
            case (state)
                IDLE: begin
                    busy_o <= 0;
                    mult_start <= 0;
                    if (start_i) begin
                        busy_o <= 1;
                        x <= (b >> 2) + 1; // Начальное приближение sqrt(b)
                        state <= CALC_SQRT;
                    end
                end

                CALC_SQRT: begin
                    // Вычисляем квадратный корень через итерации
                    for (i = 0; i < 15; i = i + 1) begin
                        temp1 = x * x; // Квадрат текущего приближения
                        temp2 = $signed({1'b0, b}) - $signed(temp1); // Разница
                        if (temp2 >= 0) begin
                            x_next = x + (temp2 / (2 * x + 1)); // Коррекция
                        end else begin
                            x_next = x - (x >> 1); // Уменьшение
                        end
                        if (x_next > 255) x_next = 255;
                        if (x_next < 0) x_next = 0;
                        x = x_next;
                    end
                    sqrt_result <= x; // Сохраняем результат sqrt(b)
                    state <= CALC_MULT;
                end

                CALC_MULT: begin
                    // Запуск умножения
                    if (!mult_busy && !mult_start) begin
                        mult_start <= 1;
                    end else if (mult_busy) begin
                        mult_start <= 0;
                    end

                    // Проверяем, завершено ли умножение
                    if (!mult_busy && mult_start == 0) begin
                        y <= mult_result; // Обновляем результат умножения
                        state <= DONE;
                    end
                end

                DONE: begin
                    busy_o <= 0;      // Снимаем флаг занятости
                    state <= IDLE;    // Переход в ожидание
                end
            endcase
        end
    end
endmodule
