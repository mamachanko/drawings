var Device = require('../src/Device');
var Surface = require('../src/Surface');

describe('Device', function () {

    describe('given a window and an html document', function () {

        var device, window, document, body, canvas;

        beforeEach(function () {
            window = {innerWidth: 124, innerHeight: 457};

            canvas = jasmine.createSpyObj('canvas', ['getContext']);
            canvas.getContext.and.returnValue('aContext');

            document = jasmine.createSpyObj('document', ['createElement']);
            body = jasmine.createSpyObj('body', ['appendChild']);
            document.body = body;
            document.createElement.and.returnValue(canvas);

            device = new Device(window, document);
        });

        describe('when initialized', function () {

            beforeEach(function () {
                device.init();
            });

            it('it creates an identifiable and window-filling canvas element', function () {
                expect(document.createElement).toHaveBeenCalledWith('canvas')
                expect(canvas.id).toEqual('canvas');
                expect(canvas.width).toEqual(124);
                expect(canvas.height).toEqual(457);
                expect(document.body.appendChild).toHaveBeenCalledWith(canvas);
            });

            it('it returns a Surface', function () {
                expect(device.getSurface()).toEqual(new Surface('aContext'));
                expect(canvas.getContext).toHaveBeenCalledWith('2d');
            });
        });
    });
});