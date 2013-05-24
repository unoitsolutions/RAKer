//
//  MainVC.m
//  Raker
//
//  Created by Fuentes, Pinuno [PRI-1PP] on 5/24/13.
//  Copyright (c) 2013 UNO IT Solutions, LLC. All rights reserved.
//

#import "MainVC.h"

@interface MainVC ()

- (UIView *)_createNewPage:(BOOL)visible;

@end

@implementation MainVC

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.pageUno = [self _createNewPage:YES];
    [self.view addSubview:self.pageUno];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

#pragma mark - Action Methods

- (void)UISwipeGestureRecognizerHandler:(UISwipeGestureRecognizer *)swipe
{
    DLOG(@"");
    self.pageDos = [self _createNewPage:NO];
    [self.view addSubview:self.pageDos];
    
    [UIView animateWithDuration:0.4
                     animations:^{
                         self.pageUno.center = CGPointMake(self.pageUno.center.x - self.view.bounds.size.width, self.pageUno.center.y);
                         self.pageDos.center = CGPointMake(self.pageDos.center.x - self.view.bounds.size.width, self.pageDos.center.y);
                     }
                     completion:^(BOOL finished) {
                         self.pageUno = self.pageDos;
                     }];
}

#pragma mark - Convenience Methods

- (UIView *)_createNewPage:(BOOL)visible
{
    CGRect frame = self.view.bounds;
    frame.origin.x = self.view.bounds.size.width * (visible ? 0 : 1);
    
    CGFloat hue = ( arc4random() % 256 / 256.0 );  //  0.0 to 1.0
    CGFloat saturation = ( arc4random() % 128 / 256.0 ) + 0.5;  //  0.5 to 1.0, away from white
    CGFloat brightness = ( arc4random() % 128 / 256.0 ) + 0.5;  //  0.5 to 1.0, away from black
    UIColor *color = [UIColor colorWithHue:hue saturation:saturation brightness:brightness alpha:1];
    
    UIView *view = [[UIView alloc] initWithFrame:frame];
    [view setBackgroundColor:color];
    
    UISwipeGestureRecognizer *swipe = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(UISwipeGestureRecognizerHandler:)];
    swipe.direction = UISwipeGestureRecognizerDirectionLeft;
    [view addGestureRecognizer:swipe];
    
    return view;
}

@end
