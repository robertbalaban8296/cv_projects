var budgetController = (function() {
    var Expens = function(id, description, value) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.precentage = -1;
    };
    
    Expens.prototype.calculatePrecentage = function(totalIncome) {
        
        if (totalIncome > 0) {
            this.precentage = Math.round((this.value / totalIncome) * 100);
        }
    };
    
    Expens.prototype.getPrecentage = function() {
        return this.precentage;
    };
    
    var Incomes = function(id, description, value) {
        this.id = id;
        this.description = description;
        this.value = value;
    };
    
    var data = {
        allItems: {
            exp: [],
            inc: []
        },
        totals: {
            exp: 0,
            inc: 0
        },
        budget: 0,
        precentage: -1
    };
    
    var calculateTotal = function(type) {
        var sum = 0;
        data.allItems[type].forEach(function(cur) {
           sum += cur.value; 
        });
        data.totals[type] = sum;
    };
    
    return {
        addItem: function(type, des, val) {
            var newItem, ID;
            if (data.allItems[type].length === 0) {
                ID = 0;
            } else { 
                ID = data.allItems[type][data.allItems[type].length - 1].id + 1;
            }
            
            if (type === 'exp') {
                newItem = new Expens(ID, des, val);
            } else if (type === 'inc') {
                newItem = new Incomes(ID, des, val);
            }
            data.allItems[type].push(newItem);
            
            return newItem;
        },
        deleteItem: function(type, id) {
            var ids, index;
            
            ids = data.allItems[type].map(function(current) {
                return current.id;
            });
            
            index = ids.indexOf(id);
            
            if (index !== -1) {
                data.allItems[type].splice(index, 1);
            }
        },
        calculateBudget: function() {
            // calculate total income and expenses
            calculateTotal('exp');
            calculateTotal('inc');
            
            // calculate the budget: income - expenses
            data.budget = data.totals.inc - data.totals.exp;
            
            // calculate the precentage of income that we spent
            if (data.totals.inc > 0) {
                data.precentage = Math.round((data.totals.exp / data.totals.inc) * 100);
            }
            
        },
        getData: function() {
            console.log(data.allItems);
        },
        calculatePrecentages: function() {
            data.allItems.exp.forEach(function(cur) {
                cur.calculatePrecentage(data.totals.inc);
            });
        },
        getPrecentages: function() {
            var allPrec = data.allItems.exp.map(function(cur) {
                return cur.getPrecentage();
            });
            return allPrec;
        },
        getBudget: function() {
            return {
                budget: data.budget,
                precentage: data.precentage,
                totalInc: data.totals.inc,
                totalExp: data.totals.exp
            };
        }
    }
})();


var UIController = (function() {
    var DOMstrings = {
        inputType: '.add__type',
        inputDescription: '.add__description',
        inputValue: '.add__value',
        inputButton: '.add__btn',
        incomeContainer: '.income__list',
        expenseContainer: '.expenses__list',
        budgetValue: '.budget__value',
        incomeValue: '.budget__income--value',
        expenseValue: '.budget__expenses--value',
        precentageLabel: '.budget__expenses--percentage',
        container: '.container',
        precentage: '.item__percentage',
        date: '.budget__title--month'
    };
    
    var formatNumber = function(num, type) {
            var numSpli, int, dec, sign;
            
            num = Math.abs(num);
            num = num.toFixed(2);
            numSpli = num.split('.');
            
            int = numSpli[0];
            dec = numSpli[1];
            
            if (int.length > 3) {
                int = int.substr(0, int.length - 3) + ',' + int.substr(int.length - 3, 3);
            }
            
            type === 'exp' ? sign = '-' : sign = '+';
            
            return sign + ' ' + int + '.' + dec;
        };
    
    return {
        getInput: function() {
            return {
                type: document.querySelector(DOMstrings.inputType).value,
                description: document.querySelector(DOMstrings.inputDescription).value,
                value: parseFloat(document.querySelector(DOMstrings.inputValue).value)
            };
        },
        displayMonth: function() {
            var now, year, month;
            now = new Date();
            year = now.getFullYear();
            month = now.toString().split(' ');
            document.querySelector(DOMstrings.date).textContent = month[1] + ' ' + year;
        },
        getDOMstrings: function() {
            return DOMstrings;
        },
        addListItem: function(object, type) {
            var html, newHtml, element;
            // create HTML string with placeholder text
            if (type === 'inc') {
                element = DOMstrings.incomeContainer;
                html = '<div class="item clearfix" id="inc-%id%"> <div class="item__description">%description%</div> <div class="right clearfix"><div class="item__value">%value%</div><div class="item__delete"><button class="item__delete--btn"><i class="ion-ios-close-outline"></i></button></div></div></div>';
            } else if (type === 'exp') {
                element = DOMstrings.expenseContainer;
                html =  '<div class="item clearfix" id="exp-%id%"><div class="item__description">%description%</div><div class="right clearfix"><div class="item__value">%value%</div><div class="item__percentage">21%</div><div class="item__delete"><button class="item__delete--btn"><i class="ion-ios-close-outline"></i></button></div></div></div>';
            }
            
            // Replace the placeholder with some actual data
            newHtml = html.replace('%id%', object.id);
            newHtml = newHtml.replace('%description%', object.description);
            newHtml = newHtml.replace('%value%', formatNumber(object.value, type));
            // Insert HTMl into dom
            
            document.querySelector(element).insertAdjacentHTML('beforeend', newHtml);
        
        },
        deleteListItem: function(selectorID) {
            
            var el = document.getElementById(selectorID);
            document.getElementById(selectorID).parentNode.removeChild(el);
        
        },
        displayBudget: function(obj) {
            var type;
            
            obj.budget > 0 ? type = 'inc' : 'exp';
            document.querySelector(DOMstrings.budgetValue).textContent = formatNumber(obj.budget, type);
            document.querySelector(DOMstrings.incomeValue).textContent = formatNumber(obj.totalInc, 'inc');
            document.querySelector(DOMstrings.expenseValue).textContent = formatNumber(obj.totalExp, 'exp');
            
            if (obj.precentage > 0) {
                document.querySelector(DOMstrings.precentageLabel).textContent = obj.precentage + '%';
            } else {
                document.querySelector(DOMstrings.precentageLabel).textContent = '---';
            }
        },
        displayPrecentages: function(precentages) {
            var fields = document.querySelectorAll(DOMstrings.precentage);
            
            var forEachNodeList = function(list, callback) {
                for (var i = 0; i < list.length; i++) {
                    callback(list[i], i);
                }  
            };
            
            forEachNodeList(fields, function(node, index) {
                if (precentages[index] > 0) {
                    node.textContent = precentages[index] + '%';
                } else {
                    node.textContent = '----';
                }
            });
            
        },
        clearInput: function() {
            document.querySelector(DOMstrings.inputDescription).value = '';
            document.querySelector(DOMstrings.inputValue).value = '';
            document.querySelector(DOMstrings.inputDescription).focus();
        }
    };
})();




var controller = (function(budgetCtrl, UICtrl) {
    
    var setupEventListeners = function() {
        var DOM = UICtrl.getDOMstrings();
        document.querySelector(DOM.inputButton).addEventListener('click', ctrlAddItem);
        document.addEventListener('keypress', function(event) {
            if (event.keyCode === 13 || event.which === 13) {
                ctrlAddItem();
            }
        });
        
        document.querySelector(DOM.container).addEventListener('click', ctrlDeleteItem);
    };
    
    var updateBudget = function() {
        // 1. calculate the budget
        budgetCtrl.calculateBudget();
        // 2. return the budget
        var budget = budgetCtrl.getBudget();
        // 3. display the budget on UI
        UICtrl.displayBudget(budget);
    };
    
    var updatePrecentage = function() {
        // 1. Calculate the precentages
        budgetCtrl.calculatePrecentages();
        // 2. Read from budgetCtrl
        var precentages = budgetCtrl.getPrecentages();
        // 3. Update UI
        UICtrl.displayPrecentages(precentages);
    };
    
    var ctrlAddItem = function() {
        var input, newItem;
        //1. get input field data
        input = UICtrl.getInput();
        //2. add the item into budget controller
        if (input.description !== "" && !isNaN(input.value) && input.value > 0) {
            newItem = budgetCtrl.addItem(input.type, input.description, input.value);
            //3. add the item to the UI
            UICtrl.addListItem(newItem, input.type);
            //4. clear input
            UICtrl.clearInput();        
            //5. Calculate and update budget
            updateBudget();
            // 6. precentages
            updatePrecentage();
            
         }
    };
    
    var ctrlDeleteItem = function(event) {
        var itemID, parser, type, id;
        itemID = event.target.parentNode.parentNode.parentNode.parentNode.id;
        
        if (itemID) {
            // inc-1 sau exp-1
            parser = itemID.split('-');
            type = parser[0];
            id = parseInt(parser[1]);
            
            // 1. Delete the item from the DS
            budgetCtrl.deleteItem(type, id);
            // 2. from the ui
            UICtrl.deleteListItem(itemID);
            // 3. update and show
            updateBudget();
            // 4. precentages
            updatePrecentage();
        }
    };
    
    return {
        init: function() {
            UICtrl.displayMonth();
            UICtrl.displayBudget({
                budget: 0,
                precentage: -1,
                totalInc: 0,
                totalExp: 0
            });
            setupEventListeners();
        }
    }
    
})(budgetController, UIController);


controller.init();


