module calculator_hex (
    input  wire       clk,
    input  wire       rst,
    input  wire       button,
    input  wire [2:0] func  ,
	input  wire [7:0] num1  ,
	input  wire [7:0] num2  ,
    output reg [31:0] cal_result
);

wire rst_n = ~rst;
reg init = 1;
reg flag = 0;
wire [31:0] test = cal_result;

always @ (posedge clk or negedge rst_n) begin
    if (~rst_n) begin 
        cal_result <= 32'b0;
        init <= 1;
    end
    else if (button && init==1) begin 
        case (func)
            3'b000:cal_result <= num1 + num2;
            3'b001:cal_result <= num1 - num2;
            3'b010:cal_result <= num1 * num2;
            3'b011:cal_result <= num1 / num2;
            3'b100:cal_result <= num1 % num2;
            3'b101:cal_result <= num2 * num2;
            default:begin end
        endcase
        init <= 0;
        flag <= 0;
    end
    else if (button && flag==1) begin
        case (func)
            3'b000:cal_result <= cal_result + num2;
            3'b001:cal_result <= cal_result - num2;
            3'b010:cal_result <= cal_result * num2;
            3'b011:cal_result <= cal_result / num2;
            3'b100:cal_result <= cal_result % num2;
            3'b101:cal_result <= cal_result * cal_result;
            default:begin end
         endcase
         flag <= 0;
     end
     else if (~button) flag <=1;
end
endmodule