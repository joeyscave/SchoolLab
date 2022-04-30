module memory_w_r (
    input  wire         clk  , 
    input  wire         clk_g ,
	input  wire         rst   ,
	input  wire         button,
    input  wire [15:0]  mem_douta,
    output wire         mem_ena,
    output wire         mem_wea,
    output wire [3:0]   mem_addra,
    output wire [15:0]  mem_dina,
	output wire [15:0]  led
);

reg ena = 1;
reg wea = 0;
reg [3:0] addra = 4'b0000;
reg [15:0] dina = 16'b0000_0000_0000_0000;
reg [15:0] out = 16'b0000_0000_0000_0000;

assign mem_ena = ena;
assign mem_wea = wea;
assign mem_addra = addra;
assign mem_dina = dina;
assign led = out;

wire rst_n = ~rst;

reg [23:0] cnt = 24'd0;

parameter TOTAL = 24'd1_000_0000;
wire cnt_end = (cnt == TOTAL);
wire cnt_inc = (ena == 1 && wea == 0);

always @(posedge clk_g or negedge rst_n) begin
    if (~rst_n) cnt <= 24'd0;
    else if (cnt_end) cnt <= 24'd0;
    else if (cnt_inc) cnt <= cnt + 24'd1;
end

always @(posedge clk_g or negedge rst_n) begin
    if (~rst_n) begin
        addra <= 4'd0;
        wea <= 0;
        ena <= 0;
        dina <= 16'b0000_0000_0000_0001;
        out <= 16'b0000_0000_0000_0000;
    end
    else if (button) begin
        ena <= 1;
        wea <= 1;
        addra <= 4'b0000;
        dina <= 16'b0000_0000_0000_0001;
    end
    else if (ena == 1) begin
        if (wea == 1) begin
            if (addra == 4'b1111) begin
                addra <= 4'b0000;
                wea <= 0;
            end
            else begin
                addra <= addra + 4'b1;
                dina <= {dina[14:0], dina[15]} + 16'b1;
            end
        end
        else if (wea == 0 && cnt_end) begin
            out <= mem_douta;
            if (addra != 4'b1111) begin
                addra <= addra + 4'b1;
            end
        end
    end
end

endmodule