let graph_click_enabled = false;

const elt = document.getElementById('calculator');
const calculator = Desmos.GraphingCalculator(elt, {
    keypad: false,
    expressions: false,
    settingsMenu: false,
    invertedColors: true,
    xAxisLabel: 'x',
    yAxisLabel: 'y',
    xAxisStep: 1,
    yAxisStep: 1,
    xAxisArrowMode: Desmos.AxisArrowModes.POSITIVE,
    yAxisArrowMode: Desmos.AxisArrowModes.POSITIVE
});

calculator.setMathBounds({
    left: -4,
    right: 4,
    bottom: -4,
    top: 4
});

let newDefaultState = calculator.getState();
calculator.setDefaultState(newDefaultState);

function drawPointXY(x, y) {
    calculator.setExpression({
        id: x + '' + y,
        latex: '(' + x + ', ' + y + ')',
        color: Desmos.Colors.RED
    });
}
function drawPoint(x, y, r) {
    calculator.setExpression({
        id: x + '' + y,
        latex: '(' + x + ', ' + y + ')',
        color: Desmos.Colors.RED
    });
    drawFig(r);
}


function drawFig(R){
    calculator.setExpression({ id: 'triangle', latex: `\\polygon((${-R}, 0), (0, 0), (0, ${-R}))`, color: Desmos.Colors.RED, opacity: 0.3});
    calculator.setExpression({ id: 'rectangle', latex: `\\polygon((0, 0), (${R}, 0), (${R}, ${-R/2}), (0, ${-R/2}))`, color: Desmos.Colors.RED, opacity: 0.3});
    calculator.setExpression({id: 'circle', latex: `r<=${R/2} \\{0\\le\\theta\\le\\frac{\\pi}{2}\\}`, color: Desmos.Colors.RED});
    calculator.setExpression({
        id: 'circle_lower_left',
        latex: `r<=${R/2} \\{-\\frac{\\pi}{2}\\le\\theta\\le\\frac{\\pi}{2}\\}`,
        color: Desmos.Colors.ORANGE
    });
}

function drawPointXYRes(x, y, result) {calculator.setExpression({
    id: x + '' + y,
    latex: '(' + x + ', ' + y + ')',
    color: result ? Desmos.Colors.PURPLE : Desmos.Colors.BLUE
});
}

function inRectangle(point, rect) {
    return (
        point.x >= rect.left &&
        point.x <= rect.right &&
        point.y <= rect.top &&
        point.y >= rect.bottom
    )
}



