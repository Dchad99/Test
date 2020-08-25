let Table = (() => {
    /**
     * Selectors
     */
    let $table;
    let $url;
    let $col;
    let $row;

    const $pageNum = $('.pageNum');
    const $search = $('.tableSearch');

    const $sortingItem = $('.line');
    const $pagingItem = $('.pagination a');
    const $next = $('.next');
    const $prev = $('.prev');

    /**
     * Constants
     */
    const DEFAULT_PAGE_NUM = 10;
    /**
     * Properties
     */

    let sort = null;
    let filters = null;
    let currentPage = null;
    let totalEntries = null;

    /**
     * Private methods
     */

    const setTotalEntries = total => totalEntries = total;

    const getTotalEntries = () => {
        return totalEntries;
    };

    const loadTable = () => {
        $.post($url, {filters, sort, currentPage}).done(entriesData => {

            if (!entriesData) {
                return;
            }

            let {totalEntries, entries} = entriesData;

            setTotalEntries(totalEntries);

            let listNum = ``;
            const size_of_pages = Math.ceil(getTotalEntries() / filters.pageNum);

            let array = [];
            let head = [];
            let tail = [];

            for (let i = 1; i <= size_of_pages; i++) {
                array.push(i);
                //    listNum += `<a href="#" data-page="${i}">${i}</a>`;
            }
            let last = array.length;

            head = array.slice(0, 2);
            tail = array.slice(array.length - 1, array.length);


            array.splice(0, 2);
            array.splice(array.length - 1, array.length);
            let result = [...head, `...`, ...tail];
            console.log(result);

            if (currentPage == 1) {
                $prev.css({"display": "none"});
            } else {
                $prev.css({"display": "block"});
            }

            if (currentPage > getTotalEntries()) {
                $next.css({"display": "none"});
            }

            console.log(size_of_pages);
            console.log(result);

            for(let i=1;i<=size_of_pages;i++){
                listNum += `<a href="#" data-page="${i}">${i}</a>`;
            }


            $('.align_pagination').find('.pagination').empty().html(listNum);

            const {cols, createdRow} = tablePresets;

            let tableRow = ``;

            $.each(JSON.parse(entries), (key, entry) => {

                tableRow += `<tr ${createdRow(entry)}>`;

                cols.forEach(({col, createdCell, render}) => {
                    tableRow += `<td>${render((entry[col] || null), entry)}</td>`;
                });

                tableRow += `</tr>`;
            });
            $table.find('tbody').html(tableRow);
        });
    };


    const resetSortViewOnCol = col => {
        // reset data-sort to "none" in "col"
        if (!col || !col.length) return;

        $(`#${col}`).data('sort', 'none')
            .find('i.muted').removeClass('muted');

        return true;
    };


    const getSortDirView = dir => {
        switch (dir) {
            case 'none':
                return {
                    current: `<i class="fa fa-angle-up"></i>
                                <i class="fa fa-angle-down"></i>`,
                    next: {
                        dir: 'asc', view: `<i class="fa fa-angle-up"></i>
                                <i class="fa fa-angle-down muted"></i>`
                    }
                };
            case 'asc':
                return {
                    current: `<i class="fa fa-angle-up"></i>
                                <i class="fa fa-angle-down muted"></i>`,
                    next: {
                        dir: 'desc', view: `<i class="fa fa-angle-up muted"></i>    
                                <i class="fa fa-angle-down"></i>`
                    }
                };
            case 'desc':
                return {
                    current: `<i class="fa fa-angle-up muted"></i>
                                <i class="fa fa-angle-down"></i>`,
                    next: {
                        dir: 'none', view: `<i class="fa fa-angle-up muted"></i>
                                <i class="fa fa-angle-down muted"></i>`
                    }
                };
        }
    };

    return {
        init: function (object) {

            let {tableSelector, url} = object;

            let selectorTab = tablePresets.tableSelector;
            $url = tablePresets.url;

            $table = $(selectorTab);

            sort = {col: '', dir: ''};
            filters = {pageNum: DEFAULT_PAGE_NUM, search: ''};
            currentPage = 1;

            totalEntries = 0;

            let sc = new WebSocket('ws://localhost:8080/actions')
            sc.onmessage = (data) => {

                let a = JSON.parse(data.data);

                let tableRow = ``;

                const {cols, createdRow} = tablePresets;

                $.each(a, (key, entry) => {

                    tableRow += `<tr>`;

                    cols.forEach(({col, createdCell, render}) => {
                        tableRow += `<td>${render((entry[col] || null), entry)}</td>`;
                    });

                    tableRow += `</tr>`;
                });
                $table.find('tbody').html(tableRow);
            }
            this.event();
        },

        getFilters: function () {
            return filters;
        },

        setFilters: newFilters => filters = newFilters,

        getSort: function () {
            return sort;
        },

        setSort: newSort => sort = newSort,

        getPage: function () {
            return currentPage;
        },

        setPage: page => currentPage = page,


        loadTable,

        event: function () {
            document.addEventListener('DOMContentLoaded', loadTable);

            $pageNum.on('change', e => {
                let filters = {...this.getFilters()};

                filters.pageNum = e.currentTarget.value;
                if(filters.pageNum === 0){
                    filters.pageNum = DEFAULT_PAGE_NUM;
                }

                const sizeRows = getTotalEntries();

                const size_of_pages = Math.ceil(sizeRows / filters.pageNum);
                console.log(size_of_pages);

                let listNum = ``;
                for(let i=1;i<=size_of_pages;i++){
                    listNum += `<a href="#" data-page="${i}">${i}</a>`;
                }
                console.log(listNum);

                $('.align_pagination').find('.pagination').empty().html(listNum);
                this.setFilters(filters);

                loadTable();
            });

            $search.on('keyup', e => {
                const $target = e.currentTarget;

                let filters = {...this.getFilters()};

                filters.search = $target.value;
                this.setFilters(filters) && loadTable();
            });


            $(document).on('click', '.line', e => {
                const $target = $(e.currentTarget);

                let {col} = {...this.getSort()};

                if ($target.prop('id') !== col) {
                    resetSortViewOnCol(col);
                }

                const {dir, view} = getSortDirView($target.data('sort')).next;
                $target.data('sort', dir).find('.icon_sort').empty().html(view);
                this.setSort({col: $target.prop('id'), dir}) && loadTable();
            });

            $(document).on('click', '.pagination a', e => {
                const $target = $(e.currentTarget);

                this.setPage(parseInt($target.data('page')) || 0) && loadTable();
            });

            $prev.on('click', e => {
                const $target = $(e.currentTarget);
                if (currentPage === 1) {
                    $target.prop({"disabled": "true"}).addClass('disable');
                    currentPage = 1;
                    this.setPage(currentPage);
                } else {
                    currentPage = this.getPage() - 1;
                    console.log("Prev current page: " + currentPage);
                    this.setPage(currentPage) && loadTable();
                }
                console.log(currentPage);
            });

            $next.on('click', e => {
                const $target = $(e.currentTarget);

                const maxSize = Math.ceil(getTotalEntries() / filters.pageNum);

                if (currentPage === maxSize) {
                    $target.prop({"disabled": "true"});
                    currentPage = maxSize;
                } else if (currentPage > 1) {
                    $prev.removeClass('disable');
                } else {
                    currentPage = currentPage + 1;
                    this.setPage(currentPage) && loadTable();
                }

            });
        }
    }
})();