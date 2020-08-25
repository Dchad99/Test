const tablePresets = {
    tableSelector: '.table',
    url: '/StudentTableServlet',

    createdRow: function (entry){
        return `data-id="${entry.ID}"`;
    },

    cols: [
        {
            col: 'ID',
            sort: 'asc',
            render: function(colData) {
                return colData;
            }
        },
        {
            col: 'Name',
            sort: 'none',
            render: function(colData) {
                return colData;
            }
        },
        {
            col: 'Surname',
            sort: 'none',
            render: function(colData) {
                return colData;
            }
        },
        {
            col: 'City',
            sort: 'none',
            render: function(colData) {
                return colData;
            }
        },
        {
            col: 'Email',
            sort: 'none',
            render: function(colData) {
                return `<div class="email">${colData}</div>`;
            }
        },

        {
            col: 'Status',
            sort: false,
            render: function(colData, {Status}) {
                if (Status === 'absent') {
                    return `<span class="user_blocked">${colData}</i></span>`;
                } else {
                    return `<span class="user_status">${colData}  <i class="fa fa-hand-paper"></span>`;
                }
            }
        },

    ],
};
Table.init(tablePresets);


